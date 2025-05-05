package edu.du.ict4315.parking.models;

import edu.du.ict4315.parking.charges.strategy.EntryAndExitChargeStrategy;
import edu.du.ict4315.parking.charges.strategy.EntryOnlyChargeStrategy;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategy;
import edu.du.ict4315.parking.constants.Discounts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.UUID;

public class ParkingLot extends Observable<ParkingEvent> {
    public final String id;
    public final String name;
    public final Address address;
    private final Money dailyRate;
    public final LotType lotType;
    private final HashMap<ParkingPermit, LocalDateTime> permitsInLot;
    private final ParkingChargeStrategy parkingChargeStrategy;

    public ParkingLot(String name, Address address, Money dailyRate, LotType lotType) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.dailyRate = dailyRate;
        this.lotType = lotType;
        this.permitsInLot = new HashMap<>();
        if (lotType == LotType.ENTRY_ONLY) {
            this.parkingChargeStrategy = new EntryOnlyChargeStrategy();
        } else if (lotType == LotType.ENTRY_AND_EXIT) {
            this.parkingChargeStrategy = new EntryAndExitChargeStrategy();
        } else {
            throw new IllegalArgumentException("Invalid lotType");
        }
    }

    // todo - this is really confusing and unpredictable

    /**
     * Handles the entry of a car into a lot.
     * If this is an entry-only lot, we generate and return a ParkingTransaction immediately.
     * If this is an entry and exit lot, we wait until exit to generate a transaction.
     * @param parkingPermit The permit on the car that is entering the lot.
     * @param entryTime The exact time the vehicle is entering the lot.
     * @return A transaction if this is an entry-only lot, otherwise null.
     */
    public ParkingTransaction scanOnEntry(ParkingPermit parkingPermit, LocalDateTime entryTime) {
        if (this.lotType == LotType.ENTRY_ONLY) {
            // Parking transactions are created at the time of entry.
            // Therefore, we need to return a transaction now.

            ParkingEvent parkingEvent = new ParkingEvent(
                ParkingEvent.EventType.ENTRY,
                this,
                parkingPermit,
                entryTime
            );

            this.notifyObservers(parkingEvent);

            return new ParkingTransaction(
                LocalDate.from(entryTime),
                parkingPermit,
                this,
                this.parkingChargeStrategy.calculateCharge(
                    this.getDailyRate(parkingPermit.getCar().getType()),
                    entryTime,
                    null
                )
            );
        }

        if (this.lotType == LotType.ENTRY_AND_EXIT) {
            // Charges accrue for the entire time the vehicle is parked.
            // Therefore, we need to keep track of when it entered,
            // but do not want to return a transaction yet.
            this.permitsInLot.put(parkingPermit, entryTime);
            return null;
        }

        throw new RuntimeException("Invalid lot type");
    }

    /**
     * Handles the exit of a car from an ENTRY_AND_EXIT lot.
     * This should NOT be called for an ENTRY_ONLY lot, or it will throw an exception.
     * @param parkingPermit The permit on the car that is exiting the lot.
     * @param exitTime The exact time the vehicle is entering the lot.
     * @return The transaction for this ENTRY_AND_EXIT lot, now that it knows when the vehicle entered and exited.
     */
    public ParkingTransaction scanOnExit(ParkingPermit parkingPermit, LocalDateTime exitTime) {
        if (this.lotType == LotType.ENTRY_ONLY) {
            // This type of lot does not scan on exit.
            throw new RuntimeException("This is an ENTRY_ONLY lot and does not have an exit scanner");
        }

        // Find the record of when this vehicle entered the lot.
        LocalDateTime entryTime = this.permitsInLot.get(parkingPermit);

        // Remove the vehicle.
        this.permitsInLot.remove(parkingPermit);

        ParkingEvent parkingEvent = new ParkingEvent(
            ParkingEvent.EventType.EXIT,
            this,
            parkingPermit,
            exitTime
        );

        this.notifyObservers(parkingEvent);

        // Calculate the charge.
        return new ParkingTransaction(
            LocalDate.from(exitTime),
            parkingPermit,
            this,
            this.parkingChargeStrategy.calculateCharge(
                this.getDailyRate(parkingPermit.getCar().getType()),
                entryTime,
                exitTime
            )
        );
    }

    public Money getDailyRate(Car.CarType carType) {
        if (carType == Car.CarType.COMPACT) {
            return Money.applyDiscount(this.dailyRate, Discounts.COMPACT_DISCOUNT);
        }
        return this.dailyRate;
    }

    public enum LotType {
        // Entry and exit monitoring:
        // For parking lots that monitor vehicles and entry and exit,
        // charges accrue for the entire time the vehicle is parked;
        // the parking transaction is created at the time of exit.
        ENTRY_AND_EXIT,

        // Entry only monitoring:
        // For lots monitoring only entry, the University has the traffic department check each lot
        // between midnight and 6 am. If any car is found in the lot at that time,
        // it is entered for another day's charge at the same rate as if it just entered the lot at that time;
        // parking transactions are created at the time of entry.
        ENTRY_ONLY
    }
}
