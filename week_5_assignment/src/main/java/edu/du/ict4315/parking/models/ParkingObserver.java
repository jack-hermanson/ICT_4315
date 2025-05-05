package edu.du.ict4315.parking.models;

import edu.du.ict4315.parking.TransactionManager;

public class ParkingObserver implements Observer<ParkingEvent> {

    public TransactionManager transactionManager = new TransactionManager();

    @Override
    public void update(ParkingEvent event) {
        this.transactionManager.park(event);
    }
}
