package edu.du.ict4315;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.List;

public class ParkingOffice {
    private String parkingOfficeName;
    private List<Customer> listOfCustomers;
    private List<ParkingLot> listOfParkingLots;
    private Address parkingOfficeAddress;

    public String getParkingOfficeName() {
        return ""; // todo
    }

    public void register(Customer customer) {
        // todo - why does this return void but registering a car returns a permit?
    }

    public ParkingPermit register(Car car) {
        // todo - is this what this is supposed to do?
        // todo - where does expiration come from?
        // todo - we just return the permit and don't do anything with it?
        return new ParkingPermit(
            car,
            LocalDate.now().plusYears(1)
        );
    }

    public ParkingTransaction park(LocalDate date, ParkingPermit parkingPermit, ParkingLot parkingLot) {
        return new ParkingTransaction(date, parkingPermit, parkingLot, new Money(0)); // todo
    }

    public Money getParkingCharges(ParkingPermit parkingPermit) {
        return new Money(0); // todo
    }

    public Money getParkingCharges(Customer customer) {
        return new Money(0); // todo
    }

}
