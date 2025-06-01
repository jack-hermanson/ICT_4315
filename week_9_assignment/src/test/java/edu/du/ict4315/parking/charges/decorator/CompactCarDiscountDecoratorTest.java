package edu.du.ict4315.parking.charges.decorator;

import edu.du.ict4315.parking.models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CompactCarDiscountDecoratorTest {

    Car car = new Car(
        Car.CarType.COMPACT,
        "",
        new Customer(
            "",
            "",
            "",
            new Address(
                "",
                "",
                "",
                "",
                "")
        )
    );

    ParkingPermit permit = new ParkingPermit(
        this.car,
        LocalDate.MAX
    );

    ParkingLot parkingLot = new ParkingLot(
        "",
        new Address("","","","",""),
        new Money(5,0),
        ParkingLot.LotType.ENTRY_ONLY
    );

    @Test
    void testCompactCarDiscount() {
        ParkingChargeCalculator calculator = new CompactCarDiscountDecorator(
            new FlatRateCalculator(this.parkingLot.getDailyRate())
        );

        Money result = calculator.getParkingCharge(
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(2),
            this.parkingLot,
            this.permit
        );

        assertEquals(
            new Money(4,0),
            result
        );
    }
}