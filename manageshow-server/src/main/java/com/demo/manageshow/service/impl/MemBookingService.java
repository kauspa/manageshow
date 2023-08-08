package com.demo.manageshow.service.impl;

import com.demo.manageshow.data.Booking;
import com.demo.manageshow.data.Buyer;
import com.demo.manageshow.data.Seat;
import com.demo.manageshow.data.Show;
import com.demo.manageshow.service.BookingService;
import com.demo.manageshow.service.ConflictException;
import com.demo.manageshow.service.InvalidException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MemBookingService implements BookingService {
    private final ConcurrentHashMap<Show, ConcurrentHashMap<Seat, Booking>> showSeatBookingMap = new ConcurrentHashMap<>();
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
        Map<Seat, Booking> seatMap = showSeatBookingMap.get(show);
        //validate if booking exists
        //create booking exists for this user
        Optional<Booking> existingBookingOpt = seatMap.values().parallelStream().filter(b -> b.getBuyer().equals(buyer)).findFirst();
        if (existingBookingOpt.isPresent()) {
            throw new InvalidException(String.format("Booking found %s. Cannot book again.", existingBookingOpt.get()));
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
        }

        return result;
    }


    @Override
    public Booking cancelTicket(Buyer buyer, Show show) {

        return null;
    }

    @Override
    public Set<Booking> getBookingByBuyer(Buyer buyer) {
        return null;
    }

    @Override
    public Set<Booking> getBookingByShow(Show show) {
        return null;
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
