package com.demo.manageshow.data;

import java.util.Objects;

public class Show {
    private final String showId;
    private String showName;
    private final int rows, seats;
    private int cancellationMinutesAfterBooking;

    public Show(String showId, int rows, int seats) {
        this.showId = showId;
        this.rows = rows;
        this.seats = seats;
        this.cancellationMinutesAfterBooking = 2;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowId() {
        return showId;
    }

    public int getRows() {
        return rows;
    }

    public int getSeats() {
        return seats;
    }

    @Override
    public int hashCode() {
        return Objects.hash(showId);
    }

    public int getCancellationMinutesAfterBooking() {
        return cancellationMinutesAfterBooking;
    }

    public void setCancellationMinutesAfterBooking(int cancellationMinutesAfterBooking) {
        this.cancellationMinutesAfterBooking = cancellationMinutesAfterBooking;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Show)) {
            return false;
        } else {
            Show other = (Show) obj;
            return Objects.equals(showId, other.showId);
        }
    }

    @Override
    public String toString() {
        return "Show{" +
                "showId='" + showId + '\'' +
                ", showName='" + showName + '\'' +
                ", rows=" + rows +
                ", seats=" + seats +
                '}';
    }
}
