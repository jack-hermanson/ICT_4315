package edu.du.ict4315.parking.charges.strategy;

import com.google.inject.Singleton;
import edu.du.ict4315.parking.models.Money;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Singleton
public class EntryAndExitChargeStrategy implements ParkingChargeStrategy {

    @Override
    public Money calculateCharge(Money dailyRateAfterDiscounts, LocalDateTime entryTime, LocalDateTime exitTime) {
        // For ENTRY_AND_EXIT lots, you MUST give it the exit time. It MUST be known.
        if (exitTime == null) {
            throw new IllegalArgumentException("Exit time must be provided for ENTRY_AND_EXIT lots");
        }

        // Count the number of days between the entry and exit
        int daysParked = 1;
        for (
            LocalDate start = LocalDate.from(entryTime);
            start.isBefore(LocalDate.from(exitTime));
            start = start.plusDays(1)
        ) {
            daysParked++;
        }

        return Money.multiply(dailyRateAfterDiscounts, daysParked);
    }
}
