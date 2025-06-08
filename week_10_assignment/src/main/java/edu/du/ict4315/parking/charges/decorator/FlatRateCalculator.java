package edu.du.ict4315.parking.charges.decorator;

import edu.du.ict4315.parking.models.Money;
import edu.du.ict4315.parking.models.ParkingLot;
import edu.du.ict4315.parking.models.ParkingPermit;

import java.time.LocalDateTime;

public class FlatRateCalculator extends ParkingChargeCalculator {

    private final Money flatRate;

    public FlatRateCalculator(Money flatRate) {
        this.flatRate = flatRate;
    }

    @Override
    public Money getParkingCharge(LocalDateTime entry, LocalDateTime exit, ParkingLot parkingLot, ParkingPermit permit) {
        return this.flatRate;
    }
}
