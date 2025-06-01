package edu.du.ict4315.parking.charges.decorator;

import edu.du.ict4315.parking.models.Money;
import edu.du.ict4315.parking.models.ParkingLot;
import edu.du.ict4315.parking.models.ParkingPermit;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class ParkingChargeCalculator {
    public abstract Money getParkingCharge(LocalDateTime entry,
                                           LocalDateTime exit,
                                           ParkingLot parkingLot,
                                           ParkingPermit permit);
}
