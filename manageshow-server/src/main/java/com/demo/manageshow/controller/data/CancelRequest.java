package com.demo.manageshow.controller.data;

public class CancelRequest {
    private final String ticketNo, mobile;

    public CancelRequest(String ticketNo, String mobile) {
        this.ticketNo = ticketNo;
        this.mobile = mobile;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public String getMobile() {
        return mobile;
    }

    @Override
    public String toString() {
        return "CancelRequest{" +
                "ticketNo='" + ticketNo + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
