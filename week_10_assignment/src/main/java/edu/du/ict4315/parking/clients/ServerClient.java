package edu.du.ict4315.parking.clients;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

public class ServerClient {
    private static final String COMMAND_URL = "http://localhost:8000/command";

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Adding a short delay while we wait for the server to start...");
        Thread.sleep(2_000);

        if (args.length < 2) {
            System.out.println("Please use the following syntax:\n" +
                "java ServerClient <CAR|CUSTOMER> key=value [key=value ...]");
        }

        String requestJson = requestArgsToJson(args);

        // Send POST request
        URL url = new URL(COMMAND_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(requestJson.getBytes());
            os.flush();
        }

        // Read the response
        // Read the response
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                System.out.println(responseLine);
            }
        }

        connection.disconnect();
    }

    private static String requestArgsToJson(String[] args) {
        String commandName = args[0]; // CAR, CUSTOMER, ENTER, EXIT (enter/exit are new for week 10)

        Properties properties = new Properties();
        String[] parameters = Arrays.copyOfRange(args, 1, args.length);
        for (String parameter : parameters) {
            String[] splitCommand = parameter.split("=", 2);
            properties.setProperty(splitCommand[0], splitCommand[1]);
        }

        // Create a request object
        ParkingRequest request = new ParkingRequest(commandName, properties);
        return request.toJson();
    }
}
