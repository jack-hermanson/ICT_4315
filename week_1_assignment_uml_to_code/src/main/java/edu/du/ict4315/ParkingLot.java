package edu.du.ict4315;

import java.util.UUID;

public class ParkingLot {
    private final String id;
    private final String name;
    private final Address address;

    public ParkingLot(String name, Address address) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
    }

    public Money getDailyRate(CarType carType) {
        return new Money(0);
        // todo
    }
}
