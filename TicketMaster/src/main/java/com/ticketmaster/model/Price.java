package com.ticketmaster.model;

public class Price implements TicketEntity{

    private final Double price;

    public Price(Double price) {
        this.price = price;
    }


    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.valueOf(price);
    }
}
