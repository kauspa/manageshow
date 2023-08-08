package com.demo.manageshow.service;

import com.demo.manageshow.data.Booking;
import com.demo.manageshow.data.Buyer;
import com.demo.manageshow.data.Seat;
import com.demo.manageshow.data.Show;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookingService {
    Booking bookTicket(Buyer buyer,Show show, Set<String> reserveSeats);
    Booking cancelTicket(Buyer buyer,String tobeCancelledTicketNo);
    Set<Booking> getBookingByBuyer(Buyer buyer);
    Set<Booking> getBookingByShow(Show show);
    Set<Seat> getAvailabilityByShow(Show show);
    Optional<Booking> getBookingByTicketNo(String ticketNo);
}
