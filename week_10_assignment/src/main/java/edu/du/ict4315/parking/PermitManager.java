package edu.du.ict4315.parking;

import com.google.inject.Singleton;
import edu.du.ict4315.parking.models.Car;
import edu.du.ict4315.parking.models.ParkingPermit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
public class PermitManager {
    private final List<ParkingPermit> permits;

    public PermitManager() {
        this.permits = Collections.synchronizedList(new ArrayList<>());
    }

    public void register(Car car) {
        ParkingPermit newPermit = new ParkingPermit(car, LocalDate.MAX);
        this.permits.add(newPermit);
        System.out.println("Permit created successfully for " + newPermit.getCar().getLicensePlate() + "; " +
            "permits count: " + this.permits.size());
    }

    public ParkingPermit getPermit(String licensePlate) {
        var matchingPermit = this.permits.stream()
            .filter(x -> x.getCar().getLicensePlate().equalsIgnoreCase(licensePlate)).findFirst();

        if (matchingPermit.isEmpty()) {
            throw new RuntimeException("Permit with license plate " + licensePlate + " not found");
        }

        return matchingPermit.get();
    }
}
