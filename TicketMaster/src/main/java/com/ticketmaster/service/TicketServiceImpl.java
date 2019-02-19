package com.ticketmaster.service;

import com.ticketmaster.model.Seat;
import com.ticketmaster.model.SeatHold;
import com.ticketmaster.model.SeatStatus;
import com.ticketmaster.mgr.TicketManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TicketServiceImpl implements TicketService {
    @Override
    public int numSeatsAvailable() {
        List<Seat> seats = getAvailableSeats();
        return seats.size();
    }


    private List<Seat> getAvailableSeats (){
        List<Seat> seats = TicketManager.getTicketManager().getAllSeats();
        List<Seat> ret= new ArrayList<Seat>();
        for(Seat s : seats){
            if(s.getStatus().equals(SeatStatus.Available)){
                ret.add(s);
            }
        }
        return ret;
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        if(numSeats <= 0){
           throw new RuntimeException("Seats should be positive natural number");
        }
        List<Seat> seats = getAvailableSeats();

        List<Seat> seatsToHold = new ArrayList<Seat>();
        for(Seat s : seats){
            if(numSeats == 0){
                break;
            }
            seatsToHold.add(s);
            numSeats--;
        }
        for(Seat s : seatsToHold){
            synchronized (s){
                s.setStatus(SeatStatus.Hold);
            }
        }
        SeatHold seatHold = new SeatHold(seatsToHold, new Date());
        TicketManager.getTicketManager().holdSeat(seatHold);
        return seatHold;

    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
        SeatHold hold = TicketManager.getTicketManager().findSeatHoldById(seatHoldId);
        if(hold == null){
            throw new RuntimeException(" NO Seats Holded found...");
        }
        StringBuilder msg = new StringBuilder(" Congratulations!!! Your seats details are : \n");
        for(Seat s : hold.getSeats()){
            TicketManager.getTicketManager().bookSeat(s, customerEmail);
            msg.append("["+ s+ "] \n ");
        }
        return msg.toString();
    }
}
