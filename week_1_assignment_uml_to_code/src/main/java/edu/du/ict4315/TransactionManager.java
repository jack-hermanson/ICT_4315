package edu.du.ict4315;

import java.time.LocalDate;
import java.util.List;

// todo - how is this connected to ParkingOffice?
public class TransactionManager {
    private List<ParkingTransaction> transactions;

    public ParkingTransaction park(LocalDate date, ParkingPermit parkingPermit, ParkingLot parkingLot) {
        throw new RuntimeException();
        // todo
    }

    public Money getParkingCharges(ParkingPermit parkingPermit) {
        return new Money(0);
        // todo
    }

    public Money getParkingCharges(Customer customer) {
        return new Money(0);
        // todo
    }


}
