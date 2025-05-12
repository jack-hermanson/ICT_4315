package edu.du.ict4315.parking;

import edu.du.ict4315.parking.charges.factory.ParkingChargeStrategyFactory;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategy;
import edu.du.ict4315.parking.models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    public final List<ParkingTransaction> transactions = new ArrayList<>();

    public ParkingTransaction park(ParkingEvent parkingEvent) {
        ParkingTransaction transaction = this.park(
            parkingEvent.permit,
            parkingEvent.parkingLot,
            parkingEvent.eventType == ParkingEvent.EventType.ENTRY ? parkingEvent.getTimestamp() : null,
            parkingEvent.eventType == ParkingEvent.EventType.EXIT ? parkingEvent.getTimestamp() : null
        );
        this.transactions.add(transaction);
        return transaction;
    }

    public ParkingTransaction park(ParkingPermit permit, ParkingLot parkingLot, LocalDateTime entry, LocalDateTime exit) {
        ParkingChargeStrategyFactory parkingChargeStrategyFactory = new ParkingChargeStrategyFactory();
        ParkingChargeStrategy chargeStrategy = parkingChargeStrategyFactory.getStrategy(parkingLot);
        Money charge = chargeStrategy.calculateCharge(
            parkingLot.getDailyRate(permit.getCar().getType()),
            entry,
            exit
        );
        ParkingTransaction transaction = new ParkingTransaction(LocalDate.now(), permit, parkingLot, charge);
        this.transactions.add(transaction);
        return transaction;
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
