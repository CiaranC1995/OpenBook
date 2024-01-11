package com.openBook.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testParamConstructorAndGetters() {
        Book book = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald",
                "Scribner", 1925, "Fiction");

        assertEquals(1, book.getId());
        assertEquals("The Great Gatsby", book.getBookName());
        assertEquals("F. Scott Fitzgerald", book.getAuthor());
        assertEquals("Scribner", book.getPublisher());
        assertEquals(1925, book.getYearOfPublish());
        assertEquals("Fiction", book.getGenre());

        assertDoesNotThrow(() -> {
            new Book(1, "The Great Gatsby", "F. Scott Fitzgerald",
                    "Scribner", 1925, "Fiction");
        });
    }

    @Test
    void testDefaultConstructor() {
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
    void testEqualsAndHashCode() {
        Book book1 = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald",
                "Scribner", 1925, "Fiction");
        Book book2 = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald",
                "Scribner", 1925, "Fiction");

        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    void testNotEquals() {
        Book book1 = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald",
                "Scribner", 1925, "Fiction");
        Book book2 = new Book(2, "To Kill a Mockingbird", "Harper Lee",
                "J.B. Lippincott & Co.", 1960, "Novel");

        assertNotEquals(book1, book2);
        assertNotEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    void testNotEqualsDifferentId() {
        Book book1 = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald",
                "Scribner", 1925, "Fiction");
        Book book2 = new Book(2, "The Great Gatsby", "F. Scott Fitzgerald",
                "Scribner", 1925, "Fiction");

        assertNotEquals(book1, book2);
        assertNotEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    void testToString() {
        Book book = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald",
                "Scribner", 1925, "Fiction");

        String expectedString = "Book{id=1, bookName='The Great Gatsby', author='F. Scott Fitzgerald'" +
                ", publisher='Scribner', yearOfPublish=1925, genre='Fiction'}";
        assertEquals(expectedString, book.toString());
    }

}