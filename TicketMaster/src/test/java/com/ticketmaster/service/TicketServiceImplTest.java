package com.ticketmaster.service;

import com.ticketmaster.model.Seat;
import com.ticketmaster.model.SeatHold;
import com.ticketmaster.model.SeatStatus;
import com.ticketmaster.mgr.SeatMatrix;
import com.ticketmaster.mgr.TicketManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class TicketServiceImplTest {

    private TicketServiceImpl unit = new TicketServiceImpl();


    @Before
    public void setup(){
        TicketManager.getTicketManager().init();
    }

    @Test
    public void testNumSeatsAvailable_Orchestra() throws Exception {
            assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow())));
    }
    @Test
    public void testNumSeatsAvailable_Main() throws Exception {
            assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow())));
    }
    @Test
    public void testNumSeatsAvailable_All() throws Exception {
            assertThat(unit.numSeatsAvailable(), is(1250));
    }

    @Test
    public void testFindAndHoldSeats() throws Exception {
        assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow())));
        unit.findAndHoldSeats(2,  "test@test.test");
        assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow()) - 2));
    }

    @Test
    public void testFindAndHoldSeats_AndFurtherHOlder() throws Exception {
        TicketManager.holdExpire = 2;
        assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow())));
        int bookSeats = (SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow()) + 2;
        unit.findAndHoldSeats(bookSeats, "test@test.test");
        assertThat(unit.numSeatsAvailable(), is(0));
    }
    @Test(expected = Exception.class)
    public void testFindAndHoldSeats_Exception() throws Exception {
        unit.findAndHoldSeats(0, "test@test.test" );
    }


    @Test
    public void testFindAndHoldSeats_AndExpire() throws Exception {
        Optional<Integer> o = Optional.empty();
        TicketManager.holdExpire = 5;
        assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow())));
        unit.findAndHoldSeats(1, "test@test.test");
        assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow())- 1));
        System.out.println(" TICKET SHOULD BE HOLDED TILL : " + TicketManager.holdExpire +" Seconds");
        System.out.println(" So will be Checking after " + TicketManager.holdExpire + " Seconds ..... ");
        Thread.sleep(7000);
        System.out.println(" Checking after " + TicketManager.holdExpire +" Seconds ! ");
        assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow())));
        System.out.println(" Seat seems available ... ");
    }

    @Test
    public void testReserveSeats() throws Exception {
        assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow())));
        SeatHold hold = unit.findAndHoldSeats(1, "test@test.test");
        assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow()) - 1 ));
        unit.reserveSeats(hold.getId(), "test@test.test");
        assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow()) -1 ));
        Seat seat =  TicketManager.getTicketManager().findSeatById(hold.getSeats().get(0).getId());
        assertThat(seat, is(notNullValue()));
        assertThat(seat.getStatus(), is(SeatStatus.Reserve));

    }

    @Test
    public void testReserveSeats_AfterExpiryTime() throws Exception {
        Optional<Integer> o = Optional.empty();
        TicketManager.holdExpire = 5;
        assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow())));
        SeatHold hold = unit.findAndHoldSeats(1, "test@test.test");
        assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow()) -1 ));
        unit.reserveSeats(hold.getId(), "test@test.test");
        assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow()) -1 ));
        Seat seat =  TicketManager.getTicketManager().findSeatById(hold.getSeats().get(0).getId());
        assertThat(seat, is(notNullValue()));
        assertThat(seat.getStatus(), is(SeatStatus.Reserve));

        System.out.println(" So will be Checking after " + TicketManager.holdExpire + " Seconds ..... ");
        Thread.sleep(7000);
        System.out.println(" Checking after " + TicketManager.holdExpire +" Seconds ! ");
        assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow()) -1 ));

    }
    @Test(expected = Exception.class)
    public void testReserveSeats_AfterExpiryTimeReservation() throws Exception {
        TicketManager.holdExpire = 5;
        assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow())));
        SeatHold hold = unit.findAndHoldSeats(1, "test@test.test");
        assertThat(unit.numSeatsAvailable(), is((SeatMatrix.Hall.rows() * SeatMatrix.Hall.seatInRow()) -1 ));
        System.out.println(" So will be Checking after " + TicketManager.holdExpire + " Seconds ..... ");
        Thread.sleep(7000);
        System.out.println(" Checking after " + TicketManager.holdExpire +" Seconds ! ");
        unit.reserveSeats(hold.getId(), "test@test.test");

    }
}