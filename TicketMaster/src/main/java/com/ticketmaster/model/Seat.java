package com.ticketmaster.model;

public class Seat {

    private Integer id;

    private SeatStatus status;

    private Price price;

    private int row;

    private Customer customer;

    private SeatHold seatHold;


    public Seat(Integer id) {
        this.id = id;
        this.setStatus(SeatStatus.Available);
    }


    public SeatHold getSeatHold() {
        return seatHold;
    }

    public void setSeatHold(SeatHold seatHold) {
        this.seatHold = seatHold;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "\n{" +
                "'id' : " + id +""+
                ", 'price' : " + price +
                "}";
    }
}
