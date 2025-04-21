package edu.du.ict4315.parking.charges.strategy;

import edu.du.ict4315.parking.models.Money;

import java.time.LocalDate;
import java.time.Month;


public class SpecialDays {

    /**
     * List of "special days" for parking purposes.
     * Graduation is an example.
     */
    public static final LocalDate[] specialDays = new LocalDate[]{
        LocalDate.of(2025, Month.JUNE, 13),
        LocalDate.of(2025, Month.JUNE, 14),
        LocalDate.of(2025, Month.AUGUST, 23)
    };

    // $10/day on special days
    public static final Money specialDayDailyRate = new Money(
        10, 0
    );
}
