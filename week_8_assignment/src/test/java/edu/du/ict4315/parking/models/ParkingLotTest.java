package edu.du.ict4315.parking.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {
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
    void testScanOnEntry() {
        ParkingTransaction transaction = this.entryOnlyLot.scanOnEntry(this.permitForNormalCar, LocalDateTime.now());
        assertEquals(
            this.entryOnlyLot.getDailyRate(this.permitForNormalCar.getCar().getType()),
            transaction.getChargedAmount()
        );
    }

    @Test
    void scanOnExit() {
        this.entryAndExitLot.scanOnEntry(this.permitForNormalCar, LocalDateTime.now().minusDays(1));
        ParkingTransaction transaction = this.entryAndExitLot.scanOnExit(this.permitForNormalCar, LocalDateTime.now());
        assertEquals(
            Money.multiply(this.entryOnlyLot.getDailyRate(
                this.permitForNormalCar.getCar().getType()
            ), 2),
            transaction.getChargedAmount()
        );
    }
}