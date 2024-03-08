package com.openBook.test.config.dtoBuilder;

import com.openBook.dto.BookDto;
import com.openBook.model.Author;
import com.openBook.model.Book;
import com.openBook.test.config.builder.AuthorBuilder;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class BookDtoBuilder {

    private Long id;
    private String bookTitle;
    private Author author;
    private String publisher;
    private Integer yearOfPublish;
    private String genre;

    public BookDtoBuilder() {
        id = new Random().nextLong();
        bookTitle = RandomStringUtils.randomAlphabetic(10);
        author = new AuthorBuilder().build();
        publisher = RandomStringUtils.randomAlphabetic(10);
        yearOfPublish = new Random().nextInt();
        genre = RandomStringUtils.randomAlphabetic(10);
    }

    public BookDto build() {
        BookDto bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setTitle(bookTitle);
        bookDto.setAuthorName(author.getFirstName() + " " + author.getLastName());
        bookDto.setPublisher(publisher);
        bookDto.setYearOfPublish(yearOfPublish);
        bookDto.setGenre(genre);
        return bookDto;
    }

    public BookDtoBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public BookDtoBuilder withBookTitle(String bookName) {
        this.bookTitle = bookName;
        return this;
    }

    public BookDtoBuilder withAuthor(Author author) {
        this.author = author;
        return this;
    }

    public BookDtoBuilder withPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public BookDtoBuilder withYearOfPublish(Integer yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
        return this;
    }

    public BookDtoBuilder withGenre(String genre) {
        this.genre = genre;
        return this;
    }
}
