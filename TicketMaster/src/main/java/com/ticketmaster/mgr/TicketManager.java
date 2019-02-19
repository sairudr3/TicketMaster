package com.ticketmaster.mgr;

import com.ticketmaster.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class TicketManager {

    private static TicketManager me;
    public static  int holdExpire = 300;
    private TicketManager(){
        init();
    }

    public static TicketManager getTicketManager(){
        if(me == null){
            Integer i = 0 ;
            synchronized (i){
                if(me == null){
                    me = new TicketManager();
                }
            }
        }
        return me;
    }

    private static List<Seat> seats = new ArrayList<Seat>();
    private int ticketId = 0;


    public void  holdSeat(SeatHold seatHold){
        for(Seat s : seatHold.getSeats()){
            new TicketHolder(s , seatHold, holdExpire);
        }

    }

    public void bookSeat(Seat seat, String email){
        seat.setStatus(SeatStatus.Reserve);
        System.out.println("Seat : " + seat+" reserved for "+ email + " at "+new Date());
        Customer c = new Customer(email);
        seat.setCustomer(c);
    }

    public List<Seat> getAllSeats (){
        return seats;
    }


    public Seat findSeatById(int id){
        for(Seat s : seats){
            if(s.getId().equals( id)){
                return s;
            }
        }
        return null;
    }
    public SeatHold findSeatHoldById(int id){
        for(Seat s : seats){
            if(s.getSeatHold()!= null && s.getSeatHold().getId() ==  id){
                return s.getSeatHold();
            }
        }
        return null;
    }


    private Seat createSeat(int ticketId){
           return new Seat(ticketId);
    }

    private void initSeatsByMatrix(SeatMatrix seatMatrix){
        for(int i = 1; i <= (seatMatrix.rows() * seatMatrix.seatInRow()) ; i++){
            Seat seat = createSeat(++ticketId);
            seat.setStatus(SeatStatus.Available);
            seat.setId(++ticketId);
            seat.setPrice(new Price(seatMatrix.price()));
            seats.add(seat);
        }
    }
    public void init(){
        seats.clear();
        initSeatsByMatrix(SeatMatrix.Hall);
    }

}
