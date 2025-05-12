package edu.du.ict4315.parking.charges.decorator;

import edu.du.ict4315.parking.constants.Discounts;
import edu.du.ict4315.parking.models.Car;
import edu.du.ict4315.parking.models.Money;
import edu.du.ict4315.parking.models.ParkingLot;
import edu.du.ict4315.parking.models.ParkingPermit;

import java.time.LocalDateTime;

public class CompactCarDiscountDecorator extends ParkingChargeCalculatorDecorator {
    public CompactCarDiscountDecorator(ParkingChargeCalculator component) {
        super(component);
    }

    @Override
    public Money getParkingCharge(LocalDateTime entry, LocalDateTime exit, ParkingLot parkingLot, ParkingPermit permit) {
        Money baseCharge = this.component.getParkingCharge(entry, exit, parkingLot, permit);
        if (permit.getCar().getType() == Car.CarType.COMPACT) {
            return Money.applyDiscount(baseCharge, Discounts.COMPACT_DISCOUNT);
        }
        return baseCharge;
    }
}
