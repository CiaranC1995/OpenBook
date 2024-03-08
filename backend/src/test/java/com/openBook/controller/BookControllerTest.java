package com.openBook.controller;

import com.openBook.dto.AuthorDto;
import com.openBook.dto.BookDto;
import com.openBook.mapper.AuthorMapper;
import com.openBook.model.Author;
import com.openBook.service.BookService;
import com.openBook.test.config.builder.AuthorBuilder;
import com.openBook.test.config.dtoBuilder.BookDtoBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private BookController bookController;

    private final List<BookDto> testBooks = new ArrayList<>();

    private final Author testAuthor = new AuthorBuilder()
            .withId(1L)
            .withFirstName("Ciarán")
            .withMiddleName("Michael")
            .withLastName("Casey")
            .withNationality("Irish")
            .withYearOfBirth(1995)
            .build();

    @BeforeEach
    void beforeEach() {
        testBooks.add(new BookDtoBuilder().withId(1L).withBookTitle("BookTitle1").withAuthor(testAuthor).withGenre("Horror").build());
        testBooks.add(new BookDtoBuilder().withId(2L).withBookTitle("BookTitle2").withAuthor(testAuthor).withGenre("Comedy").build());
    }

    @Test
    void testGetAllBooks() {
        // Given
        when(bookService.getAllBooks()).thenReturn(testBooks);

        // When
        ResponseEntity<List<BookDto>> response = bookController.getAllBooks();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBooks, response.getBody());
    }

    @Test
    void testGetAllBooksWhenNoBooksExist() {
        // Given
        when(bookService.getAllBooks()).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<List<BookDto>> response = bookController.getAllBooks();

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetBookByExistingId() {
        // Given
        Long bookId = 1L;
        BookDto expectedBook = testBooks.get(0);

        when(bookService.getBookById(bookId)).thenReturn(Optional.of(expectedBook));

        // When
        ResponseEntity<BookDto> response = bookController.getBookById(bookId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBook, response.getBody());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetBookByNonexistentId() {
        // Given
        Long bookId = 100L;
        when(bookService.getBookById(bookId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<BookDto> response = bookController.getBookById(bookId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetBookByExistentTitle() {
        // Given
        String bookTitle = "Book Title";
        when(bookService.getBooksByTitle(bookTitle)).thenReturn(testBooks);

        // When
        ResponseEntity<List<BookDto>> response = bookController.getBooksByTitle(bookTitle);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBooks, response.getBody());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetBookByNonexistentTitle() {
        // Given
        String bookTitle = "Book Title";
        when(bookService.getBooksByTitle(bookTitle)).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<List<BookDto>> response = bookController.getBooksByTitle(bookTitle);

        // Then
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetBookByNullBookTitle() {
        // Given When
        ResponseEntity<List<BookDto>> response = bookController.getBooksByTitle(null);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetBookByExistentAuthor() {
        // Given
        AuthorDto authorDto = new AuthorDto();
        when(authorMapper.authorToAuthorDto(testAuthor)).thenReturn(authorDto);
        when(bookService.getBooksByAuthor(authorDto)).thenReturn(testBooks);

        // When
        ResponseEntity<List<BookDto>> response = bookController.getBooksByAuthor(authorMapper.authorToAuthorDto(testAuthor));

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBooks, response.getBody());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetBookByNonexistentAuthor() {
        // Given
        AuthorDto authorDto = new AuthorDto();
        when(authorMapper.authorToAuthorDto(testAuthor)).thenReturn(authorDto);
        when(bookService.getBooksByAuthor(authorDto)).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<List<BookDto>> response = bookController.getBooksByAuthor(authorMapper.authorToAuthorDto(testAuthor));

        // Then
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetBookByNullAuthor() {
        // Given When
        ResponseEntity<List<BookDto>> response = bookController.getBooksByAuthor(null);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetBookByExistentPublisher() {
        // Given
        String existentPublisher = "existentPublisher";
        when(bookService.getBooksByPublisher(existentPublisher)).thenReturn(testBooks);

        // When
        ResponseEntity<List<BookDto>> response = bookController.getBooksByPublisher(existentPublisher);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBooks, response.getBody());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetBookByNonexistentPublisher() {
        // Given
        String nonexistentPublisher = "nonexistentPublisher";
        when(bookService.getBooksByPublisher(nonexistentPublisher)).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<List<BookDto>> response = bookController.getBooksByPublisher(nonexistentPublisher);

        // Then
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetBookByNullPublisher() {
        // Given When
        ResponseEntity<List<BookDto>> response = bookController.getBooksByPublisher(null);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetBookByExistentYearOfPublish() {
        // Given
        Integer existentYearOfPublish = 1995;
        when(bookService.getBooksByYearOfPublish(existentYearOfPublish)).thenReturn(testBooks);

        // When
        ResponseEntity<List<BookDto>> response = bookController.getBooksByYearOfPublish(existentYearOfPublish);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBooks, response.getBody());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetBookByNonexistentYearOfPublish() {
        // Given
        Integer nonexistentYearOfPublish = 2026;
        when(bookService.getBooksByYearOfPublish(nonexistentYearOfPublish)).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<List<BookDto>> response = bookController.getBooksByYearOfPublish(nonexistentYearOfPublish);

        // Then
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetBookByNullYearOfPublish() {
        // Given When
        ResponseEntity<List<BookDto>> response = bookController.getBooksByYearOfPublish(null);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetBookByExistentGenre() {
        // Given
        String existentGenre = "Horror";
        when(bookService.getBooksByGenre(existentGenre)).thenReturn(testBooks);

        // When
        ResponseEntity<List<BookDto>> response = bookController.getBooksByGenre(existentGenre);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBooks, response.getBody());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetBookByNonexistentGenre() {
        // Given
        String nonexistentGenre = "Romance";
        when(bookService.getBooksByGenre(nonexistentGenre)).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<List<BookDto>> response = bookController.getBooksByGenre(nonexistentGenre);

        // Then
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetBookByNullGenre() {
        // Given When
        ResponseEntity<List<BookDto>> response = bookController.getBooksByGenre(null);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateBookWithValidInputs() {
        // Given
        BookDto bookDto = new BookDtoBuilder().withId(100L).build();
        when(bookService.createBook(bookDto)).thenReturn(bookDto);

        // When
        ResponseEntity<String> response = bookController.createBook(bookDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book with id=100 successfully created ...", response.getBody());
    }

    @Test
    void testCreateBookWithInvalidInputs() {
        // Given
        BookDto bookDto = new BookDtoBuilder().withId(3L).withBookTitle(null).build();
        String errorMessage = "Invalid input: bookTitle cannot be null.";
        when(bookService.createBook(bookDto)).thenThrow(new IllegalArgumentException(errorMessage));

        // When
        ResponseEntity<String> response = bookController.createBook(bookDto);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    void testDeleteBookWithExistingId() {
        // Given
        Long bookId = 1L;

        // When
        ResponseEntity<String> response = bookController.deleteBook(bookId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book with id=1 successfully deleted ...", response.getBody());
    }

    @Test
    void testDeleteBookWithNonexistentId() {
        // Given
        Long bookId = 100L;
        doThrow(EntityNotFoundException.class).when(bookService).deleteBook(bookId);

        // When
        ResponseEntity<String> response = bookController.deleteBook(bookId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteAllBooks() {
        // Given When
        ResponseEntity<String> response = bookController.deleteAllBooks();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("All books successfully deleted ...", response.getBody());
    }
}
