package edu.du.ict4315;

import java.io.FileWriter;
import java.io.IOException;

public class DatabaseTransactionLog implements TransactionLog {

    @Override
    public void logCharge(PizzaOrder order) {
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            writer.write("Charged order: " + order.toString() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
