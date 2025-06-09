package edu.du.ict4315.parking;

import com.google.inject.AbstractModule;
import edu.du.ict4315.parking.charges.strategy.EntryAndExitChargeStrategy;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategy;
import edu.du.ict4315.parking.models.ParkingLot;
import edu.du.ict4315.parking.models.Address;
import edu.du.ict4315.parking.models.Money;

public class ParkingSystemModule extends AbstractModule {
    @Override
    public void configure() {
        // For simplicity, create one strategy that will implement the ParkingChargeStrategy throughout the app.
        this.bind(ParkingChargeStrategy.class).to(EntryAndExitChargeStrategy.class);

        // Make one permit manager available throughout the application.
        this.bind(PermitManager.class).toInstance(new PermitManager());

        // For simplicity, we are going to assume there is just one parking lot, and it is entry_and_exit.
        this.bind(ParkingLot.class).toInstance(new ParkingLot(
            "Capitol Hill Lot",
            new Address("2200 S University Blvd", null, "Denver", "CO", "80210"),
            new Money(15, 0),
            ParkingLot.LotType.ENTRY_AND_EXIT
        ));

    }
}
