package com.ticketmaster.model;

import java.util.Date;
import java.util.List;
import java.util.Random;


public class SeatHold  {

    private final int id;
    private List<Seat> seats;
    private Date time;

    private static final Random r = new Random();
    public SeatHold(List<Seat> seats, Date time) {
        this.seats = seats;
        this.time = time;
        this.id = r.nextInt();
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }
}
