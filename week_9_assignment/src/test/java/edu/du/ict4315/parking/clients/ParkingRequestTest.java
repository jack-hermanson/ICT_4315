package edu.du.ict4315.parking.clients;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ParkingRequestTest {

    private ParkingRequest parkingRequest;

    @BeforeEach
    public void setUp() {
        Properties properties = new Properties();
        properties.put("license", "ROB4CO");
        properties.put("customer", "CUST1");
        this.parkingRequest = new ParkingRequest("CAR", properties);
    }

    @Test
    void testToJson() {
        String expectedJson =
            """
            {
              "commandName": "CAR",
              "properties": {
                "license": "ROB4CO",
                "customer": "CUST1"
              }
            }
            """.trim();
        assertEquals(expectedJson, this.parkingRequest.toJson());
    }

    @Test
    void testToString() {
        String expectedOutput = "Command: CAR, Properties: {license=ROB4CO, customer=CUST1}";
        assertEquals(expectedOutput, this.parkingRequest.toString());
    }

    @Test
    void testFromJson() {
        String requestJson =
            """
            {
              "commandName": "CAR",
              "properties": {
                "license": "ROB4CO",
                "customer": "CUST1"
              }
            }
            """.trim();

        assertEquals("Command: CAR, Properties: {license=ROB4CO, customer=CUST1}", ParkingRequest.fromJson(requestJson).toString());
    }
}