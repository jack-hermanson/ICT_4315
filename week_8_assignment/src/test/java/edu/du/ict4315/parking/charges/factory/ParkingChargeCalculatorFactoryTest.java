package edu.du.ict4315.parking.charges.factory;

import edu.du.ict4315.parking.charges.decorator.ParkingChargeCalculator;
import edu.du.ict4315.parking.constants.Discounts;
import edu.du.ict4315.parking.constants.SpecialDays;
import edu.du.ict4315.parking.models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class ParkingChargeCalculatorFactoryTest {

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
        new Money(1,0),
        ParkingLot.LotType.ENTRY_ONLY
    );


    @Test
    void testCreateCalculatorForSpecialDay() {
        LocalDate specialDay = LocalDate.of(2025, Month.JUNE, 13);

        LocalDateTime entry = LocalDateTime.of(specialDay, LocalTime.of(13, 0));
        LocalDateTime exit = LocalDateTime.of(specialDay, LocalTime.of(19, 0));

        ParkingChargeCalculator calculator = ParkingChargeCalculatorFactory.createCalculator(
            this.parkingLot
        );

        Money charge = calculator.getParkingCharge(entry, exit, this.parkingLot, this.permit);

        // Make sure the special date rate is charged
        assertEquals(SpecialDays.specialDayDailyRate, charge);
    }

    @Test
    void testCreateCalculatorForNormalDayCompactCar() {
        LocalDate specialDay = LocalDate.of(2025, Month.JANUARY, 15);

        LocalDateTime entry = LocalDateTime.of(specialDay, LocalTime.of(13, 0));
        LocalDateTime exit = LocalDateTime.of(specialDay, LocalTime.of(19, 0));

        ParkingChargeCalculator calculator = ParkingChargeCalculatorFactory.createCalculator(
            this.parkingLot
        );

        Money charge = calculator.getParkingCharge(entry, exit, this.parkingLot, this.permit);

        // Make sure the regular rate is charged for a compact car
        assertEquals(Money.applyDiscount(this.parkingLot.getDailyRate(), Discounts.COMPACT_DISCOUNT), charge);
    }
}