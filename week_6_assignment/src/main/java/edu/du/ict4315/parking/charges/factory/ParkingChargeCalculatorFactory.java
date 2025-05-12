package edu.du.ict4315.parking.charges.factory;

import edu.du.ict4315.parking.charges.decorator.CompactCarDiscountDecorator;
import edu.du.ict4315.parking.charges.decorator.FlatRateCalculator;
import edu.du.ict4315.parking.charges.decorator.ParkingChargeCalculator;
import edu.du.ict4315.parking.charges.decorator.SpecialDaySurchargeDecorator;
import edu.du.ict4315.parking.models.ParkingLot;
import edu.du.ict4315.parking.models.ParkingPermit;

import java.time.LocalDateTime;

public class ParkingChargeCalculatorFactory {
    public static ParkingChargeCalculator createCalculator(ParkingLot parkingLot) {
        ParkingChargeCalculator base = new FlatRateCalculator(parkingLot.getDailyRate());

        // Stack decorators.
        base = new CompactCarDiscountDecorator(base);
        base = new SpecialDaySurchargeDecorator(base);

        return base;
    }
}
