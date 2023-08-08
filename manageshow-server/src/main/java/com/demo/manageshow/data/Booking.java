package com.demo.manageshow.data;

import java.time.LocalDateTime;
import java.util.Set;

final public class Booking {
    private final String ticketNo;
    private final Show show;
    private final Buyer buyer;
    private final Set<Seat> reservedSeats;

    private final LocalDateTime bookingTime;

    public Booking(String ticketNo, Show show, Buyer buyer, Set<Seat> reservedSeats) {
        this.ticketNo = ticketNo;
        this.show = show;
        this.buyer = buyer;
        this.reservedSeats = reservedSeats;
        this.bookingTime=LocalDateTime.now();
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public Show getShow() {
        return show;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public Set<Seat> getReservedSeats() {
        return reservedSeats;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "ticketNo='" + ticketNo + '\'' +
                ", show=" + show +
                ", buyer=" + buyer +
                ", reservedSeats=" + reservedSeats +
                ", bookingTime=" + bookingTime +
                '}';
    }
}
