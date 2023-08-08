package com.demo.manageshow.service.data;

public class Seat {
    private int row;
    private int seatNo;
    private String seatName;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    @Override
    public String toString() {
        return String.format("%s", seatName);
    }

}
