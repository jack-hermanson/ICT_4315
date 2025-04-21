package edu.du.ict4315.parking.models;

public class Car {
    private final CarType type;
    private final String licensePlate;
    private final Customer owner;

    public enum CarType {
        COMPACT,
        NORMAL
    }

    public Car(CarType type, String licensePlate, Customer owner) {
        this.type = type;
        this.licensePlate = licensePlate;
        this.owner = owner;
    }

    public CarType getType() {
        return this.type;
    }

    public String getLicensePlate() {
        return this.licensePlate;
    }

    public Customer getOwner() {
        return this.owner;
    }
}
