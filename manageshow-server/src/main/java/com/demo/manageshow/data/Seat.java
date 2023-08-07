package com.demo.manageshow.data;

import java.util.AbstractSet;
import java.util.Objects;

public class Seat {
    private static final int ASCII = (int) 'A' - 1;
    private final int row;
    private final int seatNo;

    public Seat(int row, int seatNo) {
        this.row = row;
        this.seatNo = seatNo;
    }
    public Seat(String rowName, String seatNo) {
        this.row = rowName.charAt(0)- ASCII;
        this.seatNo = Integer.parseInt(seatNo);
    }

    public int getRow() {
        return row;
    }

    public String getRowName() {
        return String.valueOf((char)(ASCII + row));
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
        return "[ %s%d ]".format(getRowName(), seatNo);
    }

}
