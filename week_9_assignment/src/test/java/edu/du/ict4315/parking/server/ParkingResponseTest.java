package edu.du.ict4315.parking.server;

import edu.du.ict4315.parking.clients.ParkingRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ParkingResponseTest {

    private ParkingResponse parkingResponse;

    @BeforeEach
    void setUp() {
        this.parkingResponse = new ParkingResponse(200, "Request completed successfully");
    }

    @Test
    void toJson() {
         String expectedJson =
            """
            {
              "statusCode": 200,
              "message": "Request completed successfully"
            }
            """.trim();
        assertEquals(expectedJson, this.parkingResponse.toJson());

    }

    @Test
    void testToString() {
        String expectedString = "StatusCode: 200, Message: Request completed successfully";
        assertEquals(expectedString, this.parkingResponse.toString());
    }

    @Test
    void testFromJson() {
        String responseJson =
            """
            {
              "statusCode": 200,
              "message": "Request completed successfully"
            }
            """.trim();

        assertEquals("StatusCode: 200, Message: Request completed successfully", ParkingResponse.fromJson(responseJson).toString());
    }
}