package com.ticketmaster.service;

import com.ticketmaster.model.SeatHold;

public interface TicketService extends BaseService {


    int numSeatsAvailable();

    SeatHold findAndHoldSeats(int numSeats,  String customerEmail);

    String reserveSeats(int seatHoldId, String customerEmail);
}
