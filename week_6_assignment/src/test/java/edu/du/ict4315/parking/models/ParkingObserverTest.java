package edu.du.ict4315.parking.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ParkingObserverTest {

    ParkingLot parkingLot = new ParkingLot(
        "",
        new Address("","","", "", ""),
        new Money(10, 0),
        ParkingLot.LotType.ENTRY_ONLY
    );


    ParkingObserver observerOne;
    ParkingObserver observerTwo;

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


    @BeforeEach
    void setUp() {
        this.observerOne = new ParkingObserver();
        this.observerTwo = new ParkingObserver();
        this.parkingLot.abbObserver(this.observerOne);
        this.parkingLot.abbObserver(this.observerTwo);
    }

    @Test
    void testAllObserversGetEvent() {
        // Make sure both get the same transaction because they were both listening.
        this.parkingLot.scanOnEntry(this.permitForNormalCar, LocalDateTime.now());
        ParkingTransaction observerOneFirst = this.observerOne.transactionManager.transactions.getFirst();
        ParkingTransaction observerTwoFirst = this.observerTwo.transactionManager.transactions.getFirst();
        assertEquals(observerOneFirst.getChargedAmount(), observerTwoFirst.getChargedAmount());
        assertEquals(observerOneFirst.getDate(), observerTwoFirst.getDate());
    }
}