package com.demo.manageshow.service.data;

public class Show {
    private String showId;
    private String showName;
    private  int rows, seats;
    private int cancellationMinutesAfterBooking;

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getCancellationMinutesAfterBooking() {
        return cancellationMinutesAfterBooking;
    }

    public void setCancellationMinutesAfterBooking(int cancellationMinutesAfterBooking) {
        this.cancellationMinutesAfterBooking = cancellationMinutesAfterBooking;
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
