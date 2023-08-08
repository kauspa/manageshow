package com.demo.manageshow.data;

import java.util.Objects;

public class Seat {
    private static final int ASCII = (int) 'A' - 1;
    private final int row;
    private final int seatNo;

    private final String seatName;

    public Seat(int row, int seatNo) {
        this.row = row;
        this.seatNo = seatNo;
        this.seatName = newSeatName();
    }

    private String newSeatName() {
        return String.valueOf((char) (ASCII + row)) + seatNo;
    }

    public static Seat parse(String seatName) {
        int row = seatName.charAt(0) - ASCII;
        int seatNo = Integer.parseInt(seatName.substring(1));
        return new Seat(row, seatNo);
    }
    public int getRow() {
        return row;
    }

    public String getSeatName() {
        return seatName;
    }

    public int getSeatNo() {
        return seatNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, seatNo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Seat)) {
            return false;
        } else {
            Seat other = (Seat) obj;
            return Objects.equals(row, other.row)
                    && Objects.equals(seatNo, other.seatNo);
        }
    }

    @Override
    public String toString() {
        return String.format("[ %s ]",seatName);
    }

}
