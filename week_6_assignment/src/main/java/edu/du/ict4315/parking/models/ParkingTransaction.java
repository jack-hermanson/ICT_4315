package edu.du.ict4315.parking.models;

import java.time.LocalDate;

public class ParkingTransaction {
    private final LocalDate date;
    private final ParkingPermit permit;
    private final ParkingLot parkingLot;
    private final Money chargedAmount;

    public ParkingTransaction(LocalDate date, ParkingPermit permit, ParkingLot parkingLot, Money chargedAmount) {
        this.date = date;
        this.permit = permit;
        this.parkingLot = parkingLot;
        this.chargedAmount = chargedAmount;
    }

    public ParkingPermit getPermit() {
        return this.permit;
    }

    public Money getChargedAmount() {
        return this.chargedAmount;
    }

    public LocalDate getDate() {
        return this.date;
    }
}
