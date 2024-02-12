package com.openBook.service;

import com.openBook.model.Author;
import com.openBook.model.Book;
import com.openBook.repository.BookRepository;
import com.openBook.service.implementation.BookServiceImp;
import com.openBook.test.config.builder.AuthorBuilder;
import com.openBook.test.config.builder.BookBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "classpath:application-test.properties")
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImp bookService;

    private Book testBook;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Author testAuthor = new AuthorBuilder()
                .withId(2L)
                .withFirstName("Ciarán")
                .withMiddleName("Michael")
                .withLastName("Casey")
                .withNationality("Irish")
                .withYearOfBirth(1995)
                .build();

        testBook = new BookBuilder()
                .withId(1L)
                .withBookTitle("Book Title")
                .withAuthor(testAuthor)
                .withGenre("Horror")
                .withPublisher("Publisher")
                .withYearOfPublish(2024)
                .build();

    }

    @Test
    void testGetAllBooks() {
        // Given
        List<Book> books = new ArrayList<>();
        books.add(testBook);
        when(bookRepository.findAll()).thenReturn(books);

        // When
        List<Book> result = bookService.getAllBooks();

        // Then
        assertEquals(1, result.size());
        assertEquals(testBook, result.get(0));
    }

    @Test
    void testGetBookById() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        // When
        Optional<Book> result = bookService.getBookById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testBook, result.get());
    }

    @Test
    void testAddBook() {
        // When
        bookService.addBook(testBook);

        // Then
        verify(bookRepository, times(1)).save(testBook);
    }

    @Test
    void testDeleteBookById() {
        // When
        bookService.deleteBookById(1L);

        // Then
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateBook_ValidInput() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        // When
        assertDoesNotThrow(() -> bookService.updateBook(testBook));
    }

    @Test
    void testUpdateBook_BookNotFound() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // When / Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.updateBook(testBook);
        });
        assertEquals("Book with ID 1 does not exist.", exception.getMessage());
    }

    @Test
    void testUpdateBook_NullAuthor() {
        // Given
        testBook.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        testBook.setAuthor(null);

        // When / Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.updateBook(testBook);
        });
        assertEquals("Book must have an author.", exception.getMessage());
    }

    @Test
    void testUpdateBook_EmptyTitle() {
        // Given
        testBook.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        testBook.setBookTitle("");

        // When / Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.updateBook(testBook);
        });
        assertEquals("Book title cannot be empty.", exception.getMessage());
    }
}
