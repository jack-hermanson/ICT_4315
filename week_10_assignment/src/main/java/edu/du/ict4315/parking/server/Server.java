package edu.du.ict4315.parking.server;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import edu.du.ict4315.parking.ParkingSystemModule;
import edu.du.ict4315.parking.charges.strategy.EntryAndExitChargeStrategy;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategy;
import edu.du.ict4315.parking.clients.ParkingRequest;
import edu.du.ict4315.parking.models.Money;
import edu.du.ict4315.parking.models.ParkingLot;

import java.io.*;
import java.net.InetSocketAddress;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;

public class Server {
    // https://medium.com/@sayan-paul/develop-an-http-server-in-java-2137071a54a1
    public static void main(String[] args) throws IOException {

        // Dependency injection setup
        Injector injector = Guice.createInjector(new ParkingSystemModule());

        // Create server instance.
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        PostHandler handler = injector.getInstance(PostHandler.class);
        server.createContext("/command", handler);
        // server.setExecutor(null); // default - single-threaded
        server.setExecutor(Executors.newFixedThreadPool(4)); // Allow 4 threads
        System.out.println("Server is running on port 8000");
        server.start();
    }
}
