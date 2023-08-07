package com.demo.manageshow.service.impl;

import com.demo.manageshow.data.*;
import com.demo.manageshow.service.*;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MemBookingService implements BookingService {
    ConcurrentHashMap<Show, ConcurrentHashMap<Seat, Boolean>> showSeatBookingMap = new ConcurrentHashMap<>();
    AtomicLong ticketNo = new AtomicLong();

    @Override
    public Booking bookTicket(Buyer buyer, Show show, Set<Seat> seats) {
        Booking result = result = new Booking(String.valueOf(ticketNo.incrementAndGet()), show, buyer, seats);
        boolean isAnyInvalid = seats.stream().anyMatch(s ->
                (s.getRow() < 1 || s.getRow() > show.getRows()) ||
                        (s.getSeatNo() < 1 || s.getSeatNo() > show.getSeats()));
        if (isAnyInvalid) {
            throw new InvalidException("Invalid seat found in %s".format(seats.toString()));
        }
        showSeatBookingMap.putIfAbsent(show, new ConcurrentHashMap<>());
        Map<Seat, Boolean> seatMap = showSeatBookingMap.get(show);
        List<Seat> orderedSeat = new ArrayList<>(seats);
        boolean isConflict = false;
        int i = 0;
        while (i < orderedSeat.size() && !isConflict) {
            Boolean existing = seatMap.putIfAbsent(orderedSeat.get(i), Boolean.TRUE);
            if (existing != null) {
                isConflict = true;
                break;
            }
            i++;
        }
        if (isConflict) {
            for (int j = 0; j < i; j++) {
                seatMap.remove(orderedSeat.get(j));
            }
            throw new ConflictException("Seat %s was already take".format(orderedSeat.get(i).toString()));
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
}
