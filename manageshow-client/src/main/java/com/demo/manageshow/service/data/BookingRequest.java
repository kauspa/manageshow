package com.demo.manageshow.service.data;

import java.util.Set;

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

    public Set<String> getSeats() {
        return seats;
    }

    @Override
    public String toString() {
        return "BookingRequest{" +
                "showId='" + showId + '\'' +
                ", mobile='" + mobile + '\'' +
                ", seats=" + seats +
                '}';
    }
}
