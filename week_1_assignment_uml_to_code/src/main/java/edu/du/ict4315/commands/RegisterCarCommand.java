package edu.du.ict4315.commands;

import edu.du.ict4315.*;

import java.util.Objects;
import java.util.Properties;

public class RegisterCarCommand implements Command {
    private final ParkingOffice office;

    public RegisterCarCommand(ParkingOffice parkingOffice) {
        this.office = parkingOffice;
    }

    private void checkParameters(Properties properties) {
        // todo - what exactly is this supposed to do? throw an exception?
        if (!(properties.containsKey("carType") &&
            properties.containsKey("licensePlate") &&
            // todo - should we be passing a customer that was already registered? how?
            properties.containsKey("ownerFirstName") &&
            properties.containsKey("ownerLastName") &&
            properties.containsKey("ownerPhoneNumber") &&
            properties.containsKey("ownerStreetAddress1") &&
            properties.containsKey("ownerStreetAddress2") &&
            properties.containsKey("ownerCity") &&
            properties.containsKey("ownerState") &&
            properties.containsKey("ownerZip")
        )) {
            // todo - how much detail is expected?
            throw new IllegalArgumentException("Invalid properties");
        }
    }

    @Override
    public String getCommandName() {
        // todo - is this just constant?
        return "CAR";
    }

    @Override
    public String getDisplayName() {
        return ""; // todo - what is this for?
    }

    @Override
    public String execute(Properties params) {
        this.checkParameters(params);
        CarType carType = params.getProperty("carType").equalsIgnoreCase("compact")
            ? CarType.COMPACT
            : params.getProperty("carType").equalsIgnoreCase("suv")
            ? CarType.SUV
            : null;
        ParkingPermit permit = this.office.register(
            new Car(
                carType,
                params.getProperty("licensePlate"),
                new Customer(
                    params.getProperty("ownerFirstName"),
                    params.getProperty("ownerLastName"),
                    params.getProperty("ownerPhoneNumber"),
                    new Address(
                        params.getProperty("ownerStreetAddress1"),
                        params.getProperty("ownerStreetAddress2"),
                        params.getProperty("ownerCity"),
                        params.getProperty("ownerState"),
                        params.getProperty("ownerZip")
                    )
                )
            )
        );
        return permit.getCar().getLicensePlate(); // todo - what should this actually return?
    }
}
