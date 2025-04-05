package edu.du.ict4315;

import java.time.LocalDate;
import java.util.UUID;

public class ParkingPermit {
    private final String id;
    private final Car car;
    private final LocalDate expiration;

    public ParkingPermit(Car car, LocalDate expiration) {
        this.id = UUID.randomUUID().toString();
        this.car = car;
        this.expiration = expiration;
    }

    public Car getCar() {
        return this.car;
    }
}
