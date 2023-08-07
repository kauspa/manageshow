package com.demo.manageshow.data;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingRequest {
    private final String showId, mobile;
    private final Set<String> seats;

    public BookingRequest(String showId, String mobile, Set<String> seats) {
        this.showId = showId;
        this.mobile = mobile;
        this.seats = seats;
    }

    public String getShowId() {
        return showId;
    }

    public String getMobile() {
        return mobile;
    }

    public Set<Seat> getSeats() {
        if (seats != null) {
            return seats.stream().map(seat -> new Seat(seat.substring(0, 1), seat.substring(1))).collect(Collectors.toSet());
        } else {
            return Collections.<Seat>emptySet();
        }
    }
}
