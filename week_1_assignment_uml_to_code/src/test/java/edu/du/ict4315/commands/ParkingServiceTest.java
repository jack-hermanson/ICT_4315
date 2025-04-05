package edu.du.ict4315.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ParkingServiceTest {

    private ParkingService parkingService;

    @BeforeEach
    void setUp() {
        this.parkingService = new ParkingService();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void registerCustomerCommandTest() {
        String result = this.parkingService.performCommand("CUSTOMER", new String[] {
            "firstName=Jane",
            "lastName=Smith",
            "phoneNumber=3031231234",
            "streetAddress1=525 N Lincoln St",
            "streetAddress2=",
            "city=Denver",
            "state=CO",
            "zip=80203"
        });
        // todo - what is this supposed to return?
        assertEquals("OK", result);
    }

    @Test
    void registerCarCommandTest() {
        String result = this.parkingService.performCommand("CAR", new String[] {
            "ownerFirstName=Jane",
            "ownerLastName=Smith",
            "ownerPhoneNumber=3031231234",
            "ownerStreetAddress1=525 N Lincoln St",
            "ownerStreetAddress2=",
            "ownerCity=Denver",
            "ownerState=CO",
            "ownerZip=80203",
            "licensePlate=12345",
            "carType=COMPACT"
        });
        // todo - what is this supposed to return?
        assertEquals("12345", result);

    }
}