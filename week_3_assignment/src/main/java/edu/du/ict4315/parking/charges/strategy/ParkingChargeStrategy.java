package edu.du.ict4315.parking.charges.strategy;

import edu.du.ict4315.parking.models.Car;
import edu.du.ict4315.parking.models.Money;
import edu.du.ict4315.parking.models.ParkingLot;
import edu.du.ict4315.parking.models.ParkingPermit;

import java.time.LocalDateTime;

public interface ParkingChargeStrategy {

    Money calculateCharge(Money dailyRateAfterDiscounts, LocalDateTime entryTime, LocalDateTime exitTime);
}
