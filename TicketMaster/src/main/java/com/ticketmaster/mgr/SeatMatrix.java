package com.ticketmaster.mgr;

public enum SeatMatrix {
    Hall(100d, 25, 50);


    SeatMatrix(double price, int rows, int seatInRow) {
        this.price = price;
        this.rows = rows;
        this.seatInRow = seatInRow;
    }

    private double price;
    private int rows;
    private int seatInRow;

    public double price(){
        return price;
    }
    public int rows(){
        return rows;
    }
    public int seatInRow(){
        return seatInRow;
    }


}
