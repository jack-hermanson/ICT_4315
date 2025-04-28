package edu.du.ict4315.parking;

import edu.du.ict4315.parking.models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionManagerTest {

    private final TransactionManager transactionManager = new TransactionManager();

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

    private final ParkingLot entryAndExitLot = new ParkingLot(
        "",
        new Address("", "", "", "", ""),
        new Money(10, 0), // $10/day
        ParkingLot.LotType.ENTRY_AND_EXIT
    );

      private final ParkingLot entryOnlyLot = new ParkingLot(
        "",
        new Address("", "", "", "", ""),
        new Money(10, 0), // $10/day
        ParkingLot.LotType.ENTRY_ONLY
    );


      @Test
    void testEntryAndExitSingleDayCompact() {
        // Entered at 9am.
        LocalDateTime entry = LocalDateTime.of(
            LocalDate.of(2025, 1, 1),
            LocalTime.of(9, 0)
        );

        // Exited at 12pm.
        LocalDateTime exit = entry.plusHours(3);
        ParkingTransaction transaction = this.transactionManager.park(this.permitForCompactCar, this.entryAndExitLot, entry, exit);

        // Make sure the charge is only for 1 day and with the compact discount.
        assertEquals(
            this.entryAndExitLot.getDailyRate(this.permitForCompactCar.getCar().getType()),
            transaction.getChargedAmount()
        );
    }

    @Test
    void testEntryAndExitMultipleDaysNormal() {
        // Entered on January 1 at 12pm.
        LocalDateTime entry = LocalDateTime.of(
            LocalDate.of(2025, 1, 1),
            LocalTime.of(12, 0)
        );

        // Exited 3 days later, minus a few hours, but still 3 different days.
        // Jan 1 at 12pm - Jan 4 at 8am => 4 days.
        LocalDateTime exit = entry.plusDays(3).plusHours(-4);
        ParkingTransaction transaction = this.transactionManager.park(this.permitForNormalCar, this.entryAndExitLot, entry, exit);

        // Make sure the charge is for 4 days total.
        assertEquals(
            Money.multiply(this.entryAndExitLot.getDailyRate(this.permitForNormalCar.getCar().getType()), 4),
            transaction.getChargedAmount()
        );
    }

    @Test
    void testEntryOnlySingleDayCompact() {
        LocalDateTime entry = LocalDateTime.now();
        ParkingTransaction transaction = this.transactionManager.park(this.permitForCompactCar, this.entryOnlyLot, entry, null);
        assertEquals(this.entryAndExitLot.getDailyRate(this.permitForCompactCar.getCar().getType()), transaction.getChargedAmount());
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
            this.transactionManager.park(this.permitForNormalCar, this.entryOnlyLot, entry, null),
            this.transactionManager.park(this.permitForNormalCar, this.entryOnlyLot, secondDay, null),
            this.transactionManager.park(this.permitForNormalCar, this.entryOnlyLot, thirdDay, null),
        };

        // Add up all 3 days.
        Money totalCharges = new Money(0);
        for (ParkingTransaction transaction : transactions) {
            totalCharges = Money.add(totalCharges, transaction.getChargedAmount());
        }

        assertEquals(
            Money.multiply(
                this.entryOnlyLot.getDailyRate(
                    this.permitForNormalCar.getCar().getType()
                ),
                3
            ),
            totalCharges
        );
    }

}