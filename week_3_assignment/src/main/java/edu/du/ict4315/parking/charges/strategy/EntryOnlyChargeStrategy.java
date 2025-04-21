package edu.du.ict4315.parking.charges.strategy;

import edu.du.ict4315.parking.models.Money;

import java.time.LocalDateTime;

public class EntryOnlyChargeStrategy implements ParkingChargeStrategy {
    // Todo - How do we simulate the enforcement checking the lots overnight? Is that in the scope of this class?
    @Override
    public Money calculateCharge(Money dailyRateAfterDiscounts, LocalDateTime entryTime, LocalDateTime exitTime) {
        if (exitTime != null) {
            // Todo - This feels ridiculous and poorly written, but I'm trying to do what the assignment says.
            throw new IllegalArgumentException("Exit time must be null for ENTRY_ONLY lots");
        }

        // We aren't even using the entry time because it's irrelevant.
        // Todo - This is bad. Something must be wrong if we are enforcing that one arg is null and not using the other.
        return dailyRateAfterDiscounts;
    }
}
