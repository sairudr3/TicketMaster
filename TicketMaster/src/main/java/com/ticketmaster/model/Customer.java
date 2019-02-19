package com.ticketmaster.model;
import java.util.UUID;

public class Customer implements TicketEntity {

    private  UUID id;

    private final String name;


    public Customer(String name) {
        super();
        this.name = name;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

}
