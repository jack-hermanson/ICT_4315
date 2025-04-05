package edu.du.ict4315.commands;

import edu.du.ict4315.Address;
import edu.du.ict4315.Customer;
import edu.du.ict4315.ParkingOffice;

import java.util.Properties;

public class RegisterCustomerCommand implements Command {
    private final ParkingOffice office;

    public RegisterCustomerCommand(ParkingOffice office) {
        // todo - is this how we're supposed to do this? feels sloppy
        this.office = office;
    }


    private void checkParameters(Properties properties) {
        // todo - what exactly is this supposed to do? throw an exception?
        if (!(properties.containsKey("firstName") &&
            properties.containsKey("lastName") &&
            properties.containsKey("phoneNumber") &&
            properties.containsKey("streetAddress1") &&
            properties.containsKey("streetAddress2") &&
            properties.containsKey("city") &&
            properties.containsKey("state") &&
            properties.containsKey("zip")
        )) {
            // todo - how much detail is expected?
            throw new IllegalArgumentException("Invalid properties");
        }
    }

    @Override
    public String getCommandName() {
        // todo - is this just constant?
        return "CUSTOMER";
    }

    @Override
    public String getDisplayName() {
        // todo - what is this for?
        return "";
    }

    @Override
    public String execute(Properties params) {
        this.checkParameters(params);
        this.office.register(new Customer(
            params.getProperty("firstName"),
            params.getProperty("lastName"),
            params.getProperty("phoneNumber"),
            new Address(
                params.getProperty("streetAddress1"),
                params.getProperty("streetAddress2"),
                params.getProperty("city"),
                params.getProperty("state"),
                params.getProperty("zip")
            )
        ));

        // todo - what should this string return actually be?
        return "OK";
    }
}
