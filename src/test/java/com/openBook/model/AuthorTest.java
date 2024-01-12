package com.openBook.model;

import com.openBook.test.config.builder.AuthorBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    private static Validator validator;

    @BeforeAll
    public static void beforeAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testAuthorIsValid() {
        Author author = new AuthorBuilder().build();
        Set<ConstraintViolation<Author>> constraintViolationSet = validator.validate(author);
        assertThat(constraintViolationSet.size()).isEqualTo(0);
    }

    @Test
    void testParamConstructorAndGetters() {

        Author author = new Author(1, "Francis", "Scott",
                "Fitzgerald", "American", 1896);

        assertEquals(1, author.getId());
        assertEquals("Francis", author.getFirstName());
        assertEquals("Scott", author.getMiddleName());
        assertEquals("Fitzgerald", author.getLastName());
        assertEquals("American", author.getNationality());
        assertEquals(1896, author.getYearOfBirth());

        assertDoesNotThrow(() -> {
            new Author(1, "Francis", "Scott",
                    "Fitzgerald", "American", 1896);
        });
    }

    @Test
    void testDefaultConstructor() {
        Author author = new Author();

        assertNotNull(author);
        assertEquals(0, author.getId());
        assertNull(author.getFirstName());
        assertNull(author.getMiddleName());
        assertNull(author.getLastName());
        assertNull(author.getNationality());
        assertEquals(0, author.getYearOfBirth());

    }

    @Test
    void testEqualsAndHashCode() {

        Author author1 = new AuthorBuilder()
                .withId(1L)
                .withFirstName("George")
                .withMiddleName("Raymond")
                .withLastName("Martin")
                .withNationality("American")
                .withYearOfBirth(1948)
                .build();
        Author author2 = new AuthorBuilder()
                .withId(1L)
                .withFirstName("George")
                .withMiddleName("Raymond")
                .withLastName("Martin")
                .withNationality("American")
                .withYearOfBirth(1948)
                .build();

        assertEquals(author1, author2);
        assertEquals(author1.hashCode(), author2.hashCode());
    }

    @Test
    void testNotEqualsDifferentId() {
        Author author1 = new AuthorBuilder()
                .withId(1L)
                .withFirstName("George")
                .withMiddleName("Raymond")
                .withLastName("Martin")
                .withNationality("American")
                .withYearOfBirth(1948)
                .build();
        Author author2 = new AuthorBuilder()
                .withId(2L)
                .withFirstName("George")
                .withMiddleName("Raymond")
                .withLastName("Martin")
                .withNationality("American")
                .withYearOfBirth(1948)
                .build();

        assertNotEquals(author1,author2);
        assertNotEquals(author1.hashCode(), author2.hashCode());

    }

}