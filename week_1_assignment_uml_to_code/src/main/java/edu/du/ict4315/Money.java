package edu.du.ict4315;

import java.text.DecimalFormat;

public class Money {
    private final long cents;

    public Money(long cents) {
        this.cents = cents;
    }

    public Money(int dollars, int change) {
        if (dollars < 0) {
            throw new IllegalArgumentException("Dollars cannot be negative");
        }
        if (change >= 100 || change < 0) {
            throw new IllegalArgumentException("Cents cannot exceed 100 or go below 0");
        }

        this.cents = (dollars * 100L) + change;
    }

    public long getCents() {
        return this.cents;
    }

    public double getDollars() {
        double dollars = this.cents / 100f;
        return (double) Math.round(dollars * 100) / 100;
    }

    public static Money add(Money one, Money two) {
        return new Money(one.cents + two.cents);
    }

    public static Money subtract(Money one, Money two) {
        return new Money(one.cents - two.cents);
    }

    public static Money applyDiscount(Money money, double discount) {
        long newCents = Double.valueOf(money.cents - (money.cents * discount)).longValue();
        return new Money(newCents);
    }

    public static Money multiply(Money toMultiply, long multiplyBy) {
        return new Money(toMultiply.cents * multiplyBy);
    }

    public String toString() {
        double amountToFormat = this.cents / 100f;

        DecimalFormat df = new DecimalFormat("$0.00");
        return df.format(amountToFormat);
    }
}
