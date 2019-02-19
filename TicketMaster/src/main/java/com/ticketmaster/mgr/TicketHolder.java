package com.ticketmaster.mgr;

import com.ticketmaster.model.Seat;
import com.ticketmaster.model.SeatHold;
import com.ticketmaster.model.SeatStatus;

import java.util.Date;

public final class TicketHolder {


    private final Seat seat;
    private final int holdTime;

    public TicketHolder(Seat seat, SeatHold seatHold, int holdTimeInSec) {
        this.seat = seat;
        this.seat.setSeatHold(seatHold);
        this.holdTime = holdTimeInSec;
        new HoldTicketTimer(this).start();
    }

    public Seat getSeat() {
        return seat;
    }

    public SeatHold getSeatHold() {
        return seat.getSeatHold();
    }

    public int getHoldTime() {
        return holdTime;
    }


    private static class HoldTicketTimer extends Thread {
        private final TicketHolder ticketHolder;

        public HoldTicketTimer(TicketHolder ticketHolder) {
            this.ticketHolder = ticketHolder;
        }

        public void run() {
            try {
               // synchronized (ticketHolder) {
                  //  System.out.println(" HOLDED SEAT :" + ticketHolder.getSeatHold().getSeats() + " at " + new Date());
                    Date start = ticketHolder.getSeatHold().getTime();
                    if(ticketHolder.getSeatHold() == null || ticketHolder.getSeatHold().getTime()== null){
                        return;

                    }
                    long end = (start.getTime() + (ticketHolder.getHoldTime()*1000));
                    long diff =  end - System.currentTimeMillis();
                    if(diff > 0){
                        sleep(diff);
                    }

                    Seat seat = ticketHolder.getSeat();
                    if(seat.getStatus().equals(SeatStatus.Hold)){
                        seat.setStatus(SeatStatus.Available);
                        ticketHolder.getSeatHold().setTime(null);
                        ticketHolder.getSeat().setSeatHold(null);
                       // System.out.println(" RELEASED SEAT :" + seat.getId() + " at " + new Date());
                    }

                //}
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
