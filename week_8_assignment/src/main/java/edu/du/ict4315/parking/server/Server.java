package edu.du.ict4315.parking.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import edu.du.ict4315.parking.clients.ParkingRequest;

import java.io.*;
import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.concurrent.Executors;

public class Server {
    // https://medium.com/@sayan-paul/develop-an-http-server-in-java-2137071a54a1
    public static void main(String[] args) throws IOException {

        // Create server instance.
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/command", new PostHandler());
        // server.setExecutor(null); // default - single-threaded
        server.setExecutor(Executors.newFixedThreadPool(4)); // Allow 4 threads
        System.out.println("Server is running on port 8000");
        server.start();
    }

    static class PostHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Time our request handling to show that they are handled concurrently.
            long start = System.nanoTime();

            // Show that we are operating on different threads.
            System.out.println("[" + getCurrentTimeString() + "] Handling request on thread: " + Thread.currentThread().getName());

            // Make it take a while on purpose so that we send multiple requests that get handled on diff threads.
            try {
                System.out.println("Delaying 5s");
                Thread.sleep(5000); // Simulate processing delay
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
                return;
            }

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
            ParkingResponse response = new ParkingResponse(200,
                "Received request " + deserializedParkingRequest.toString());

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
            System.out.println("Took " + elapsedTimeInSeconds + " seconds");
        }
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
