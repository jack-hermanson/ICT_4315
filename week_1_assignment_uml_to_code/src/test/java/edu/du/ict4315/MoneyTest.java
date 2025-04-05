package edu.du.ict4315;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoneyTest {
    @Test
    void centsConstructor() {
        Money money = new Money(600); // $6
        assertEquals(600, money.getCents());
    }

    @Test
    void dollarsAndCents() {
        Money money = new Money(5, 20);
        assertEquals(520, money.getCents());
    }

    @Test
    void testToString() {
        Money money = new Money(759);
        assertEquals("$7.59", money.toString());
    }

    @Test
    void testAdd() {
        Money money1 = new Money(5, 25);
        Money money2 = new Money(1, 5);
        assertEquals("$6.30", Money.add(money1, money2).toString());
    }

    @Test
    void testSubtract() {
        assertEquals("$4.00", Money.subtract(new Money(4, 20),
            new Money(0, 20)).toString());
    }

    @Test
    void testDiscount() {
        Money money = new Money(1500);
        assertEquals("$1.50", Money.applyDiscount(money,0.9).toString());
    }

    @Test
    void testDollars() {
        Money money = new Money(654);
        assertEquals(6.54,money.getDollars());
    }

    @Test
    void testBadCents() {
        assertThrows(IllegalArgumentException.class, () -> new Money(50, 500));
    }

}
