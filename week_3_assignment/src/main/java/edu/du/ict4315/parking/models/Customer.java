package edu.du.ict4315.parking.models;

import java.util.UUID;

public class Customer {
    public Customer(String firstName, String lastName, String phoneNumber, Address address) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final Address address;

    public String getCustomerName() {
        return this.firstName + " " + this.lastName;
    }
}
