package com.openBook.model;

import com.openBook.test.config.builder.UserBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private static Validator validator;

    @BeforeAll
    public static void beforeAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testAccountHolderIsValid() {
        User user = new UserBuilder().build();
        Set<ConstraintViolation<User>> constraintViolationSet = validator.validate(user);
        assertThat(constraintViolationSet.size()).isEqualTo(0);
    }

    @Test
    public void testParamConstructorAndGetters() {
        User user = new User(1, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");

        assertEquals(1, user.getId());
        assertEquals("Ciarán", user.getFirstName());
        assertEquals("Casey", user.getLastName());
        assertEquals("ciarancasey@email.com", user.getEmail());
        assertEquals("password123!", user.getPassword());

        assertDoesNotThrow(() -> {
            new User(1, "Ciarán", "Casey",
                    "ciarancasey@email.com", "password123!");
        });
    }

    @Test
    public void testDefaultConstructor() {
        User user = new User();

        assertNotNull(user);
        assertEquals(0, user.getId());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
    }

    @Test
    public void testSetters() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Ciarán");
        user.setLastName("Casey");
        user.setEmail("ciarancasey@email.com");
        user.setPassword("password123!");

        assertEquals(1, user.getId());
        assertEquals("Ciarán", user.getFirstName());
        assertEquals("Casey", user.getLastName());
        assertEquals("ciarancasey@email.com", user.getEmail());
        assertEquals("password123!", user.getPassword());
    }

    @Test
    public void testEqualsAndHashCode() {
        User user1 = new User(1, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");
        User user2 = new User(1, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testNotEquals() {
        User user1 = new User(1, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");
        User user2 = new User(2, "Sarah", "Shine",
                "sarahshine@email.com", "password234!");

        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testNotEqualsDifferentId() {
        User user1 = new User(1, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");
        User user2 = new User(2, "Ciarán", "Casey",
                "ciarancasey@email.com", "password123!");

        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }
}