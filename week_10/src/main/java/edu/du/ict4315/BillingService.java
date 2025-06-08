package edu.du.ict4315;

import javax.inject.Inject;

class BillingService {
    private final CreditCardProcessor processor;
    private final TransactionLog transactionLog;

    @Inject
    BillingService(CreditCardProcessor processor,
                   TransactionLog transactionLog) {
        this.processor = processor;
        this.transactionLog = transactionLog;
    }

    public Receipt chargeOrder(PizzaOrder order, CreditCard creditCard) {
        this.transactionLog.logCharge(order);
        return new Receipt("success");
    }

}
