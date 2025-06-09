package edu.du.ict4315.parking.clients;

import com.google.gson.annotations.Expose;
import edu.du.ict4315.parking.serialization.JsonSerializer;

import java.util.Properties;

public class ParkingRequest {
    @Expose
    public final String commandName;

    @Expose
    public final Properties properties;

    public ParkingRequest(String commandName, Properties properties) {
        this.commandName = commandName;
        this.properties = properties;
    }

    public String toJson() {
        return JsonSerializer.gson.toJson(this, ParkingRequest.class);
    }

    @Override
    public String toString() {
        String output = "Command: {COMMAND_NAME}, Properties: {PROPERTIES}";
        output = output.replace("{COMMAND_NAME}", this.commandName);
        output = output.replace("{PROPERTIES}", this.properties.toString());
        return output;
    }

    public static ParkingRequest fromJson(String json) {
        return JsonSerializer.gson.fromJson(json, ParkingRequest.class);
    }
}
