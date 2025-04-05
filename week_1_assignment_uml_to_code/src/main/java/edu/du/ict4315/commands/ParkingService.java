package edu.du.ict4315.commands;

import edu.du.ict4315.ParkingOffice;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ParkingService {
    private final ParkingOffice office;
    private final Map<String, Command> commands = new HashMap<>();

    public ParkingService() {
        // todo - is this supposed to just be hardcoded in the constructor?
        this.office = new ParkingOffice();
        // todo - is this how we're supposed to register commands?
        this.register(new RegisterCustomerCommand(this.office));
        this.register(new RegisterCarCommand(this.office));
    }

    private void register(Command command) {
        // todo - check if this is actually what was expected
        this.commands.put(command.getCommandName(), command);
    }

    public String performCommand(String commandString, String[] parameters) {
        Command command = this.commands.get(commandString);

        Properties properties = new Properties();
        for (String parameter : parameters) {
            String[] splitCommand = parameter.split("=", 2);
            properties.setProperty(splitCommand[0], splitCommand[1]);
        }

        return command.execute(properties);
    }
}
