package edu.du.ict4315.parking.charges.strategy;

import edu.du.ict4315.parking.models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EntryOnlyChargeStrategyTest {

    private final ParkingPermit permitForCompactCar = new ParkingPermit(
        new Car(
            Car.CarType.COMPACT,
            "",
            new Customer(
                "",
                "",
                "",
                new Address("", "", "", "", "")
            )
        ),
        LocalDate.of(2029, 1, 1)
    );

    private final ParkingPermit permitForNormalCar = new ParkingPermit(
        new Car(
            Car.CarType.NORMAL,
            "",
            new Customer(
                "",
                "",
                "",
                new Address("", "", "", "", "")
            )
        ),
        LocalDate.of(2029, 1, 1)
    );

    private final ParkingLot parkingLot = new ParkingLot(
        "",
        new Address("", "", "", "", ""),
        new Money(10, 0), // $10/day
        ParkingLot.LotType.ENTRY_ONLY
    );

    @Test
    void testEntryOnlySingleDayCompact() {
        LocalDateTime entry = LocalDateTime.now();
        ParkingTransaction transaction = this.parkingLot.scanOnEntry(this.permitForCompactCar, entry);
        assertEquals(this.parkingLot.getDailyRate(this.permitForCompactCar.getCar().getType()), transaction.getChargedAmount());
    }

    @Test
    void testEntryOnlyMultipleDaysNormal() {
        // Entered at 12pm
        LocalDateTime entry = LocalDateTime.of(
            LocalDate.of(2025, 1, 1),
            LocalTime.of(12, 0)
        );

        // Scanned in the lot again the next day and the day after that
        LocalDateTime secondDay = entry.plusDays(1);
        LocalDateTime thirdDay = entry.plusDays(2);

        // Should have 3 days worth of transactions.
        // Simulate the enforcement vehicle scanning the permit on each of those days.
        ParkingTransaction[] transactions = new ParkingTransaction[] {
            this.parkingLot.scanOnEntry(this.permitForNormalCar, entry),
            this.parkingLot.scanOnEntry(this.permitForNormalCar, secondDay),
            this.parkingLot.scanOnEntry(this.permitForNormalCar, thirdDay)
        };

        // Add up all 3 days.
        Money totalCharges = new Money(0);
        for (ParkingTransaction transaction : transactions) {
            totalCharges = Money.add(totalCharges, transaction.getChargedAmount());
        }

        assertEquals(
            Money.multiply(
                this.parkingLot.getDailyRate(
                    this.permitForNormalCar.getCar().getType()
                ),
                3
            ),
            totalCharges
        );
    }

    @Test
    void testEntryOnlyLotsCannotScanOnExit() {
        assertThrows(
            RuntimeException.class,
            () -> this.parkingLot.scanOnExit(this.permitForNormalCar, LocalDateTime.now())
        );
    }
}