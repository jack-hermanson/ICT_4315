package edu.du.ict4315.parking.charges.decorator;

import edu.du.ict4315.parking.constants.SpecialDays;
import edu.du.ict4315.parking.models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class SpecialDaySurchargeDecoratorTest {

     Car car = new Car(
        Car.CarType.NORMAL,
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
    void testSpecialDaySurchargeDecorator() {
        LocalDate specialDay = LocalDate.of(2025, Month.JUNE, 13);

        ParkingChargeCalculator calculator = new SpecialDaySurchargeDecorator(
            new FlatRateCalculator(this.parkingLot.getDailyRate())
        );

        Money result = calculator.getParkingCharge(
            LocalDateTime.of(specialDay, LocalTime.of(13, 0)),
            LocalDateTime.of(specialDay, LocalTime.of(15, 0)),
            this.parkingLot,
            this.permit
        );

        assertEquals(
            new Money(10,0),
            result
        );
    }
}