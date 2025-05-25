package edu.du.ict4315.parking.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import edu.du.ict4315.parking.clients.ParkingRequest;

import java.io.*;
import java.net.InetSocketAddress;

public class Server {
    // https://medium.com/@sayan-paul/develop-an-http-server-in-java-2137071a54a1
    public static void main(String[] args) throws IOException {

        // Create server instance.
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/command", new PostHandler());
        server.setExecutor(null); // default
        System.out.println("Server is running on port 8000");
        server.start();
    }

    static class PostHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
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
        }
    }
}
