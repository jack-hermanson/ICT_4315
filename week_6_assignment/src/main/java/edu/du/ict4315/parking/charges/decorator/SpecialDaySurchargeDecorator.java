package edu.du.ict4315.parking.charges.decorator;

import edu.du.ict4315.parking.constants.SpecialDays;
import edu.du.ict4315.parking.models.Money;
import edu.du.ict4315.parking.models.ParkingLot;
import edu.du.ict4315.parking.models.ParkingPermit;

import java.time.LocalDateTime;

public class SpecialDaySurchargeDecorator extends ParkingChargeCalculatorDecorator {
    public SpecialDaySurchargeDecorator(ParkingChargeCalculator component) {
        super(component);
    }

    @Override
    public Money getParkingCharge(LocalDateTime entry, LocalDateTime exit, ParkingLot parkingLot, ParkingPermit permit) {
        Money baseCharge = this.component.getParkingCharge(entry, exit, parkingLot, permit);
        if (!SpecialDays.isSpecialDay(entry)) {
            return baseCharge;
        }

        // We know it is a special day, so charge the special day rate
        return SpecialDays.specialDayDailyRate;
    }


}
