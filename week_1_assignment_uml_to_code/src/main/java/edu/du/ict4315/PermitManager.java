package edu.du.ict4315;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PermitManager {
    private final List<ParkingPermit> permits;

    public PermitManager() {
        this.permits = Collections.synchronizedList(new ArrayList<>());
    }

    public ParkingPermit register(Car car) {
        ParkingPermit newPermit = new ParkingPermit(car, LocalDate.MAX);
        this.permits.add(newPermit);
        return newPermit;
    }
}
