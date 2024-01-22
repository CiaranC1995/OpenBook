package com.openBook.model;

import com.openBook.test.config.builder.AccountHolderBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class AccountHolderTest {

    private static Validator validator;

    @BeforeAll
    public static void beforeAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testAccountHolderIsValid() {
        AccountHolder accountHolder = new AccountHolderBuilder().build();
        System.out.println(accountHolder.toString());
        Set<ConstraintViolation<AccountHolder>> constraintViolationSet = validator.validate(accountHolder);
        assertThat(constraintViolationSet.size()).isEqualTo(0);
    }

    @Test
    public void testParamConstructorAndGetters() {
        AccountHolder accountHolder = new AccountHolder(1, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");

        assertEquals(1, accountHolder.getId());
        assertEquals("Ciarán", accountHolder.getFirstName());
        assertEquals("Casey", accountHolder.getLastName());
        assertEquals("ciarancasey@email.com", accountHolder.getEmail());
        assertEquals("password123!", accountHolder.getPassword());

        assertDoesNotThrow(() -> {
            new AccountHolder(1, "Ciarán", "Casey",
                    "ciarancasey@email.com", "password123!");
        });
    }

    @Test
    public void testDefaultConstructor() {
        AccountHolder accountHolder = new AccountHolder();

        assertNotNull(accountHolder);
        assertEquals(0, accountHolder.getId());
        assertNull(accountHolder.getFirstName());
        assertNull(accountHolder.getLastName());
        assertNull(accountHolder.getEmail());
        assertNull(accountHolder.getPassword());
    }

    @Test
    public void testSetters() {
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
    public void testEqualsAndHashCode() {
        AccountHolder accountHolder1 = new AccountHolder(1, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");
        AccountHolder accountHolder2 = new AccountHolder(1, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");

        assertEquals(accountHolder1, accountHolder2);
        assertEquals(accountHolder1.hashCode(), accountHolder2.hashCode());
    }

    @Test
    public void testNotEquals() {
        AccountHolder accountHolder1 = new AccountHolder(1, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");
        AccountHolder accountHolder2 = new AccountHolder(2, "Sarah", "Shine",
                "sarahshine@email.com", "password234!");

        assertNotEquals(accountHolder1, accountHolder2);
        assertNotEquals(accountHolder1.hashCode(), accountHolder2.hashCode());

    }

    @Test
    public void testNotEqualsDifferentId() {
        AccountHolder accountHolder1 = new AccountHolder(1, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");
        AccountHolder accountHolder2 = new AccountHolder(2, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");

        assertNotEquals(accountHolder1, accountHolder2);
        assertNotEquals(accountHolder1.hashCode(), accountHolder2.hashCode());
    }

}