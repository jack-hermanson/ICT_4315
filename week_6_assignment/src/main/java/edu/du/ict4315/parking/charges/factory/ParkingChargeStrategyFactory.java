package edu.du.ict4315.parking.charges.factory;

import edu.du.ict4315.parking.charges.strategy.EntryAndExitChargeStrategy;
import edu.du.ict4315.parking.charges.strategy.EntryOnlyChargeStrategy;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategy;
import edu.du.ict4315.parking.models.ParkingLot;

public class ParkingChargeStrategyFactory {

    public ParkingChargeStrategy getStrategy(ParkingLot.LotType lotType) {
        return switch (lotType) {
            case ENTRY_AND_EXIT -> new EntryAndExitChargeStrategy();
            case ENTRY_ONLY -> new EntryOnlyChargeStrategy();
            default -> throw new RuntimeException("Invalid parking lot type");
        };
    }

    public ParkingChargeStrategy getStrategy(ParkingLot parkingLot) {
        return this.getStrategy(parkingLot.lotType);
    }
}
