package com.demo.manageshow.service.data;

import java.util.Set;

final public class Booking {
    private String ticketNo;
    private Show show;
    private Buyer buyer;
    private Set<Seat> reservedSeats;
    private String bookingTime;

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Set<Seat> getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(Set<Seat> reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
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
