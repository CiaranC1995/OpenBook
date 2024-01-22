package com.openBook.model;

import com.openBook.test.config.builder.AuthorBuilder;
import com.openBook.test.config.builder.BookBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    private static Validator validator;

    @BeforeAll
    public static void beforeAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private final Author fScottFitz = new AuthorBuilder()
            .withId(1L)
            .withFirstName("Francis")
            .withMiddleName("Scott")
            .withLastName("Fitzgerald")
            .withNationality("American")
            .withYearOfBirth(1896)
            .build();

    @Test
    public void testBookIsValid() {
        Book book = new BookBuilder().build();
        Set<ConstraintViolation<Book>> constraintViolationSet = validator.validate(book);
        assertThat(constraintViolationSet.size()).isEqualTo(0);
    }

    @Test
    public void testParamConstructorAndGetters() {

        Book book = new Book(1, "The Great Gatsby", fScottFitz, "Scribner",
                1925, "Fiction");

        assertEquals(1, book.getId());
        assertEquals("The Great Gatsby", book.getBookName());
        assertEquals(fScottFitz, book.getAuthor());
        assertEquals("Scribner", book.getPublisher());
        assertEquals(1925, book.getYearOfPublish());
        assertEquals("Fiction", book.getGenre());

        assertDoesNotThrow(() -> {
            new Book(1, "The Great Gatsby", fScottFitz, "Scribner",
                    1925, "Fiction");
        });
    }

    @Test
    public void testDefaultConstructor() {
        Book book = new Book();

        assertNotNull(book);
        assertEquals(0, book.getId());
        assertNull(book.getBookName());
        assertNull(book.getAuthor());
        assertNull(book.getPublisher());
        assertEquals(0, book.getYearOfPublish());
        assertNull(book.getGenre());
    }

    @Test
    public void testEqualsAndHashCode() {

        Book book1 = new BookBuilder()
                .withId(1L)
                .withBookName("The Great Gatsby")
                .withAuthor(fScottFitz)
                .withPublisher("Scribner")
                .withYearOfPublish(1925)
                .withGenre("Fiction")
                .build();

        Book book2 = new BookBuilder()
                .withId(1L)
                .withBookName("The Great Gatsby")
                .withAuthor(fScottFitz)
                .withPublisher("Scribner")
                .withYearOfPublish(1925)
                .withGenre("Fiction")
                .build();

        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    public void testNotEqualsDifferentId() {

        Book book1 = new BookBuilder()
                .withId(1L)
                .withBookName("The Great Gatsby")
                .withAuthor(fScottFitz)
                .withPublisher("Scribner")
                .withYearOfPublish(1925)
                .withGenre("Fiction")
                .build();

        Book book2 = new BookBuilder()
                .withId(2L)
                .withBookName("The Great Gatsby")
                .withAuthor(fScottFitz)
                .withPublisher("Scribner")
                .withYearOfPublish(1925)
                .withGenre("Fiction")
                .build();

        assertNotEquals(book1, book2);
        assertNotEquals(book1.hashCode(), book2.hashCode());
    }

}