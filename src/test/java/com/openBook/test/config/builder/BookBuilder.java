package com.openBook.test.config.builder;

import com.openBook.model.Author;
import com.openBook.model.Book;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class BookBuilder {

    private Long id;
    private String bookName;
    private Author author;
    private String publisher;
    private Integer yearOfPublish;
    private String genre;

    public BookBuilder() {
        id = new Random().nextLong();
        bookName = RandomStringUtils.randomAlphabetic(100);
        author = new AuthorBuilder().build();
        publisher = RandomStringUtils.randomAlphabetic(100);
        yearOfPublish = new Random().nextInt();
        genre = RandomStringUtils.randomAlphabetic(100);
    }

    public Book build() {
        Book book = new Book();
        book.setId(id);
        book.setBookName(bookName);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setYearOfPublish(yearOfPublish);
        book.setGenre(genre);
        return book;
    }

    public BookBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public BookBuilder withBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public BookBuilder withAuthor(Author author) {
        this.author = author;
        return this;
    }

    public BookBuilder withPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public BookBuilder withYearOfPublish(Integer yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
        return this;
    }

    public BookBuilder withGenre(String genre) {
        this.genre = genre;
        return this;
    }
}
