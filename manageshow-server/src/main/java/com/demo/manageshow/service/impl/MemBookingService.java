package com.demo.manageshow.service.impl;

import com.demo.manageshow.data.Booking;
import com.demo.manageshow.data.Buyer;
import com.demo.manageshow.data.Seat;
import com.demo.manageshow.data.Show;
import com.demo.manageshow.service.BookingService;
import com.demo.manageshow.service.ConflictException;
import com.demo.manageshow.service.InvalidException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MemBookingService implements BookingService {
    // Models Show's -> Seat's -> Booking. Used for booking show tickets concurrently
    private final ConcurrentHashMap<Show, ConcurrentHashMap<Seat, Booking>> showSeatBookingMap = new ConcurrentHashMap<>();

    // Models ticketNo's -> SeatBookingMap of above showSeatBookingMap. Used for cancelling by ticketNo
    // showSeatBookingMap seatBooking map is used as reference value in ticketNoSeatBookingMap
    private final ConcurrentHashMap<String, ConcurrentHashMap<Seat, Booking>> ticketNoSeatBookingMap = new ConcurrentHashMap<>();
    // Models Show -> full seating. Used to get availability by math: (all seating - booked seat)
    // Cache for show's seating
    private final ConcurrentHashMap<Show, Set<Seat>> showSeating = new ConcurrentHashMap<>();
    private final AtomicLong ticketNo = new AtomicLong();

    @Override
    public Booking bookTicket(Buyer buyer, Show show, Set<String> reserveSeats) {
        //parse seats
        Set<Seat> seats = parseSeats(reserveSeats);
        //validate seat is not out of bounds
        boolean isAnyInvalid = seats.stream().anyMatch(s ->
                (s.getRow() < 1 || s.getRow() > show.getRows()) ||
                        (s.getSeatNo() < 1 || s.getSeatNo() > show.getSeats()));
        if (isAnyInvalid) {
            throw new InvalidException(String.format("Invalid seat found in %s. Out of bounds.", seats));
        }
        //add seat map if not present
        showSeatBookingMap.putIfAbsent(show, new ConcurrentHashMap<>());
        ConcurrentHashMap<Seat, Booking> seatMap = showSeatBookingMap.get(show);
        //validate if booking exists
        //create booking exists for this user
        Optional<Booking> existingBookingOpt = seatMap.values().parallelStream().filter(b -> b.getBuyer().equals(buyer)).findFirst();
        if (existingBookingOpt.isPresent()) {
            throw new InvalidException(String.format("Booking found %s. Only one booking per phone# is allowed per show.", existingBookingOpt.get()));
        }
        //create new booking
        Booking result = new Booking(String.valueOf(ticketNo.incrementAndGet()), show, buyer, seats);
        //start booking seats
        List<Seat> orderedSeat = new ArrayList<>(seats);
        boolean isConflict = false;
        int i = 0;
        while (i < orderedSeat.size() && !isConflict) {
            Booking existing = seatMap.putIfAbsent(orderedSeat.get(i), result);
            if (existing != null) {
                isConflict = true;
                break;
            }
            i++;
        }
        //rollback if conflict is found
        if (isConflict) {
            for (int j = 0; j < i; j++) {
                seatMap.remove(orderedSeat.get(j));
            }
            throw new ConflictException(String.format("Seat %s was already taken", orderedSeat.get(i).toString()));
        } else {
            //Maintain cache from ticketNo to share showSeatBookingMap to cancel by ticketNo
            ticketNoSeatBookingMap.put(result.getTicketNo(), seatMap);
        }

        return result;
    }


    @Override
    public Booking cancelTicket(Buyer buyer, String tobeCancelledTicketNo) {
        Optional<Booking> buyerBooking = Optional.empty();
        Map<Seat, Booking> seatBookingMap = ticketNoSeatBookingMap.get(tobeCancelledTicketNo);
        //get booked seats for the show
        if (seatBookingMap == null || seatBookingMap.isEmpty()) {
            throw new InvalidException(String.format("Ticket# %s NOT found", tobeCancelledTicketNo));
        } else {
            //get buyer booking
            buyerBooking = seatBookingMap.values().parallelStream()
                    .filter(b -> b.getBuyer().equals(buyer) && b.getTicketNo().equals(tobeCancelledTicketNo))
                    .findFirst();

            if (buyerBooking.isEmpty()) {
                throw new InvalidException(String.format("Invalid booking NOT found for user %s for Ticket# %s.",
                        buyer, tobeCancelledTicketNo));
            }

            //get threshold time
            Optional<LocalDateTime> thresholdTimeOpt = buyerBooking.map(b -> b.getBookingTime()
                    .plusMinutes(b.getShow().getCancellationMinutesAfterBooking())).stream().findFirst();
            //check cancellation criteria
            boolean isCancellable = thresholdTimeOpt.isEmpty() ? false :
                    thresholdTimeOpt.map(t -> LocalDateTime.now().isBefore(t)).orElseGet(() -> false);
            String cancellationTime = thresholdTimeOpt.orElseGet(() -> LocalDateTime.now()).toString();

            if (!isCancellable) {
                throw new InvalidException(String.format("Too late. Cancellation time was %s for Ticket# %s booking %s.",
                        cancellationTime, tobeCancelledTicketNo, buyerBooking.orElseGet(null)));
            } else {
                //cancel booking
                buyerBooking.ifPresentOrElse(b -> {
                    // remove seat booking status
                    b.getReservedSeats().forEach(seatBookingMap::remove);
                    // remove ticket booking status
                    ticketNoSeatBookingMap.remove(b.getTicketNo());
                }, () -> {
                });
            }
        }
        return buyerBooking.orElse(null);
    }

    @Override
    public Set<Booking> getBookingByBuyer(Buyer buyer) {
        return null;
    }

    @Override
    public Set<Booking> getBookingByShow(Show show) {
        Map<Seat, Booking> booking = showSeatBookingMap.get(show);

        if (booking != null) {
            return booking.values().stream().collect(Collectors.toSet());
        } else {
            return Collections.emptySet();
        }
    }

    @Override
    public Set<Seat> getAvailabilityByShow(Show show) {
        Set<Seat> seating = showSeating.get(show);
        if (seating == null) {
            seating = new HashSet<>();
            for (int r = 1; r <= show.getRows(); r++) {
                for (int s = 1; s <= show.getSeats(); s++) {
                    seating.add(new Seat(r, s));
                }
            }
            showSeating.put(show, seating);
        }
        Collection<Booking> bookings = getBookingByShow(show);
        Set<Seat> reservedSeat = bookings.stream().flatMap(b -> b.getReservedSeats().stream()).collect(Collectors.toSet());

        Set<Seat> available = new TreeSet<Seat>(Comparator.comparingInt(Seat::getRow).thenComparing(Seat::getSeatNo));
        available.addAll(seating);
        available.removeAll(reservedSeat);

        return available;
    }

    @Override
    public Optional<Booking> getBookingByTicketNo(String ticketNo) {
        return Optional.empty();
    }

    private Set<Seat> parseSeats(Set<String> seats) {
        if (seats != null && !seats.isEmpty()) {
            try {
                return seats.stream().map(Seat::parse).collect(Collectors.toSet());
            } catch (Exception e) {
                String message = String.format("Invalid seat found %s", seats);
                throw new InvalidException(message, e);
            }
        } else {
            return Collections.emptySet();
        }
    }
}
