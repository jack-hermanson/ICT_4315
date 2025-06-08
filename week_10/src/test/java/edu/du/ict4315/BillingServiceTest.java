package edu.du.ict4315;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class BillingServiceTest {

    @Test
    public void testChargeOrder() {
        Injector injector = Guice.createInjector(new BillingModule());
        BillingService service = injector.getInstance(BillingService.class);
        Receipt receipt = service.chargeOrder(new PizzaOrder(), new CreditCard());
        assertEquals("success", receipt.getStatus());
    }

    @Test
    void testChargeOrder_writesToLogFile() throws IOException {
        // start over (delete existing to make sure it actually worked)
        Path path = Path.of("log.txt");
        Files.deleteIfExists(path);

        Injector injector = Guice.createInjector(new BillingModule());
        BillingService billingService = injector.getInstance(BillingService.class);

        billingService.chargeOrder(new PizzaOrder(), new CreditCard());

        // now make sure the file exits and has "Charged order" in it
        String contents = Files.readString(path);
        assertTrue(contents.contains("Charged order"));
    }
}
