package com.openBook.mapper;

import com.openBook.dto.BookDto;
import com.openBook.model.Book;
import com.openBook.test.config.builder.BookBuilder;
import com.openBook.test.config.dtoBuilder.BookDtoBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookMapperTest {

    private BookMapper mapper;

    private static Book testBookOne;
    private static Book testBookTwo;

    private static BookDto testBookDtoOne;

    @BeforeEach
    public void beforeEach() {
        mapper = BookMapper.INSTANCE;
    }

    @BeforeAll
    public static void beforeAll() {
        testBookOne = new BookBuilder().build();
        testBookTwo = new BookBuilder().build();
        testBookDtoOne = new BookDtoBuilder().build();
    }

    @Test
    public void testBookToBookDto() {
        // Given testBookOne
        String concatenatedAuthorName = testBookOne.getAuthor().getFirstName() + " " + testBookOne.getAuthor().getLastName();

        // When
        BookDto bookDto = mapper.bookToBookDto(testBookOne);

        // Then
        assertEquals(testBookOne.getId(), bookDto.getId());
        assertEquals(testBookOne.getTitle(), bookDto.getTitle());
        assertEquals(concatenatedAuthorName, bookDto.getAuthorName());
        assertEquals(testBookOne.getPublisher(), bookDto.getPublisher());
        assertEquals(testBookOne.getYearOfPublish(), bookDto.getYearOfPublish());
        assertEquals(testBookOne.getGenre(), bookDto.getGenre());
    }

    @Test
    public void testBooksToBookDtos() {
        // Given
        List<Book> books = Arrays.asList(testBookOne, testBookTwo);

        // When
        List<BookDto> bookDtos = mapper.booksToBookDtos(books);

        // Then
        assertEquals(books.size(), bookDtos.size());
        for (int i = 0; i < books.size(); i++) {

            String concatenatedAuthorName = books.get(i).getAuthor().getFirstName() + " "
                    + books.get(i).getAuthor().getLastName();

            assertEquals(books.get(i).getId(), bookDtos.get(i).getId());
            assertEquals(books.get(i).getTitle(), bookDtos.get(i).getTitle());
            assertEquals(concatenatedAuthorName, bookDtos.get(i).getAuthorName());
            assertEquals(books.get(i).getPublisher(), bookDtos.get(i).getPublisher());
            assertEquals(books.get(i).getYearOfPublish(), bookDtos.get(i).getYearOfPublish());
            assertEquals(books.get(i).getGenre(), bookDtos.get(i).getGenre());

        }
    }

//    @Test
//    public void testBookDtoToBook() {
//        // Given testBookDtoOne
//
//        // When
//        Book book = mapper.bookDtoToBook(testBookDtoOne);
//
//        System.out.println(book.toString());
//        assertEquals(testBookDtoOne.getId(), book.getId());
//        assertEquals(testBookDtoOne.getTitle(), book.getTitle());
//        assertEquals(testBookDtoOne.getAuthorName(), book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName());
//        assertEquals(testBookDtoOne.getPublisher(), book.getPublisher());
//        assertEquals(testBookDtoOne.getGenre(), book.getGenre());
//        assertEquals(testBookDtoOne.getYearOfPublish(), book.getYearOfPublish());
//    }
}
