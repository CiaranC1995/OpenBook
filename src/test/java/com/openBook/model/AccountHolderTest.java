package com.openBook.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountHolderTest {

    @Test
    void testParamConstructorAndGetters() {
        AccountHolder accountHolder = new AccountHolder(1, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");

        assertEquals(1, accountHolder.getId());
        assertEquals("Ciarán", accountHolder.getFirstName());
        assertEquals("Casey", accountHolder.getLastName());
        assertEquals("ciarancasey@email.com", accountHolder.getEmail());
        assertEquals("password123!", accountHolder.getPassword());

        assertNotNull(accountHolder);
    }

    @Test
    void testDefaultConstructor() {
        AccountHolder accountHolder = new AccountHolder();

        assertEquals(0, accountHolder.getId());
        assertNull(accountHolder.getFirstName());
        assertNull(accountHolder.getLastName());
        assertNull(accountHolder.getEmail());
        assertNull(accountHolder.getPassword());

        assertNotNull(accountHolder);
    }

    @Test
    void testSetters() {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(1);
        accountHolder.setFirstName("Ciarán");
        accountHolder.setLastName("Casey");
        accountHolder.setEmail("ciarancasey@email.com");
        accountHolder.setPassword("password123!");

        assertEquals(1, accountHolder.getId());
        assertEquals("Ciarán", accountHolder.getFirstName());
        assertEquals("Casey", accountHolder.getLastName());
        assertEquals("ciarancasey@email.com", accountHolder.getEmail());
        assertEquals("password123!", accountHolder.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        AccountHolder accountHolder1 = new AccountHolder(1, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");
        AccountHolder accountHolder2 = new AccountHolder(1, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");

        assertEquals(accountHolder1, accountHolder2);
        assertEquals(accountHolder1.hashCode(), accountHolder2.hashCode());
    }

    @Test
    void testNotEquals() {
        AccountHolder accountHolder1 = new AccountHolder(1, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");
        AccountHolder accountHolder2 = new AccountHolder(2, "Sarah", "Shine",
                "sarahshine@email.com", "password234!");

        assertNotEquals(accountHolder1, accountHolder2);
        assertNotEquals(accountHolder1.hashCode(), accountHolder2.hashCode());

    }

    @Test
    void testNotEqualsDifferentId() {
        AccountHolder accountHolder1 = new AccountHolder(1, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");
        AccountHolder accountHolder2 = new AccountHolder(2, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");

        assertNotEquals(accountHolder1, accountHolder2);
        assertNotEquals(accountHolder1.hashCode(), accountHolder2.hashCode());
    }

}