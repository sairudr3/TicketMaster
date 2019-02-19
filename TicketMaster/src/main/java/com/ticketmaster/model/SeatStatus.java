package com.ticketmaster.model;

public enum SeatStatus {

    Hold("HOLD"),
    Reserve("RESERVE"),
    Available("AVAILABLE");


    private String status;

    SeatStatus(String status) {
        this.status = status;
    }

    public String status(){
        return status;
    }
}
