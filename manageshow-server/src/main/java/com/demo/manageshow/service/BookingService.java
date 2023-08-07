package com.demo.manageshow.service;

import com.demo.manageshow.data.*;

import java.util.Optional;
import java.util.Set;

public interface BookingService {
    public Booking bookTicket(Buyer buyer,Show show, Set<Seat> seats);
    public Booking cancelTicket(Buyer buyer,Show show);
    public Set<Booking> getBookingByBuyer(Buyer buyer);
    public Set<Booking> getBookingByShow(Show show);
    public Optional<Booking> getBookingByTicketNo(String ticketNo);
}
