package edu.du.ict4315.parking.charges.strategy;

import edu.du.ict4315.parking.models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class EntryAndExitChargeStrategyTest {

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
        ParkingLot.LotType.ENTRY_AND_EXIT
    );

    @Test
    void testEntryAndExitSingleDayCompact() {
        // Entered at 9am.
        LocalDateTime entry = LocalDateTime.of(
            LocalDate.of(2025, 1, 1),
            LocalTime.of(9, 0)
        );
        this.parkingLot.scanOnEntry(this.permitForCompactCar, entry);

        // Exited at 12pm.
        LocalDateTime exit = entry.plusHours(3);
        ParkingTransaction transaction = this.parkingLot.scanOnExit(this.permitForCompactCar, exit);

        // Make sure the charge is only for 1 day and with the compact discount.
        assertEquals(
            this.parkingLot.getDailyRate(this.permitForCompactCar.getCar().getType()),
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
        this.parkingLot.scanOnEntry(this.permitForNormalCar, entry);

        // Exited 3 days later, minus a few hours, but still 3 different days.
        // Jan 1 at 12pm - Jan 4 at 8am => 4 days.
        LocalDateTime exit = entry.plusDays(3).plusHours(-4);
        ParkingTransaction transaction = this.parkingLot.scanOnExit(this.permitForNormalCar, exit);

        // Make sure the charge is for 4 days total.
        assertEquals(
            Money.multiply(this.parkingLot.getDailyRate(this.permitForNormalCar.getCar().getType()), 4),
            transaction.getChargedAmount()
        );
    }
}