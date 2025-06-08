package edu.du.ict4315;

import com.google.inject.AbstractModule;

public class BillingModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(TransactionLog.class).to(DatabaseTransactionLog.class);
        this.bind(CreditCardProcessor.class).to(PaypalCreditCardProcessor.class);
    }
}
