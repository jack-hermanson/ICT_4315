package edu.du.ict4315.parking.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import edu.du.ict4315.parking.PermitManager;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategy;
import edu.du.ict4315.parking.clients.ParkingRequest;
import edu.du.ict4315.parking.models.*;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Singleton
public class PostHandler implements HttpHandler {

    ParkingChargeStrategy strategy;
    ParkingLot parkingLot;
    PermitManager permitManager;

    @Inject
    public PostHandler(ParkingChargeStrategy strategy, ParkingLot parkingLot, PermitManager permitManager) {
        this.strategy = strategy;
        this.parkingLot = parkingLot;
        this.permitManager = permitManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Time our request handling to show that they are handled concurrently.
        long start = System.nanoTime();

        // Show that we are operating on different threads.
        System.out.println("[" + getCurrentTimeString() + "] Handling request on thread: " + Thread.currentThread().getName());

        // Require POST request
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            return;
        }

        // Read request body
        InputStream inputStream = exchange.getRequestBody();
        String requestBody = new BufferedReader(new InputStreamReader(inputStream))
            .lines()
            .reduce("", (acc, line) -> acc + line);

        // Deserialize into an object
        ParkingRequest deserializedParkingRequest = ParkingRequest.fromJson(requestBody);

        // Do something with the newly created object
        String result = this.interpretRequest(deserializedParkingRequest);
        ParkingResponse response = new ParkingResponse(200, result);

        // Convert response to JSON
        String jsonResponse = response.toJson();

        // Write response back to requester
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(jsonResponse.getBytes());
        outputStream.flush();
        outputStream.close(); // Gotta flush and close everything out when done.
        System.out.println("[" + getCurrentTimeString() + "] Request has been completed on thread " + Thread.currentThread().getName());
        double elapsedTimeInSeconds = (double) (System.nanoTime() - start) / 1_000_000_000;
        System.out.println("Took " + Math.round(elapsedTimeInSeconds) + " seconds");
    }

    private String interpretRequest(ParkingRequest parkingRequest) {

        // Handle any delays (so we can sequentially register, enter, and then exit)
        String delay = parkingRequest.properties.getProperty("delay");
        if (delay != null) {
            long milliseconds = Long.parseLong(delay);
            try {
                System.out.println("Delaying " + milliseconds + "ms");
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // Handle creation of new car
        if (parkingRequest.commandName.equalsIgnoreCase("CAR")) {
            // Create new car
            String plate = parkingRequest.properties.getProperty("license");
            this.permitManager.register(new Car(
                Car.CarType.NORMAL, // hardcode normal for simplicity
                plate, // get license plate from request
                // hardcode customer for simplicity:
                new Customer("Driver", "One", "1234567890", new Address(
                    "", "", "", "", ""
                ))
            ));

            return "Successfully registered car with license plate: " + plate;
        }

        // Handle entering the lot
        if (parkingRequest.commandName.equalsIgnoreCase("ENTER")) {
            // Get the permit using the license plate
            String plate = parkingRequest.properties.getProperty("license");
            ParkingPermit parkingPermit = this.permitManager.getPermit(plate);

            // Park in the lot
            this.parkingLot.scanOnEntry(parkingPermit, LocalDateTime.now());
            return "Car with license plate " + plate + " successfully entered lot";
        }

        // Handle exiting the lot
        if (parkingRequest.commandName.equalsIgnoreCase("EXIT")) {
            // Get the permit using the license plate
            String plate = parkingRequest.properties.getProperty("license");
            ParkingPermit parkingPermit = this.permitManager.getPermit(plate);

            // Park in the lot
            this.parkingLot.scanOnExit(parkingPermit, LocalDateTime.now());
            return "Car with license plate " + plate + " successfully exited lot";
        }

        return "Unknown command";
    }

    /**
     * Hacky method that just quickly returns a timestamp so that we can see when something is happening.
     *
     * @return A formatted timestamp like 18:55:21
     */
    static String getCurrentTimeString() {
        return Instant.now().toString().split("T")[1].split("\\.")[0];
    }
}