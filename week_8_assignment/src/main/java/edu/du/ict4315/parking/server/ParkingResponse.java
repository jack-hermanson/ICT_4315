package edu.du.ict4315.parking.server;

import com.google.gson.annotations.Expose;
import edu.du.ict4315.parking.clients.ParkingRequest;
import edu.du.ict4315.parking.serialization.JsonSerializer;

public class ParkingResponse {
    @Expose
    private final int statusCode;

    @Expose
    private final String message;

    public ParkingResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public String toJson() {
        return JsonSerializer.gson.toJson(this);
    }

    @Override
    public String toString() {
        String output = "StatusCode: {STATUS_CODE}, Message: {MESSAGE}";
        output = output.replace("{STATUS_CODE}", Integer.toString(this.statusCode));
        output = output.replace("{MESSAGE}", this.message);
        return output;
    }

    public static ParkingResponse fromJson(String json) {
        return JsonSerializer.gson.fromJson(json, ParkingResponse.class);
    }
}
