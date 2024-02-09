package com.openBook.repository;

import com.openBook.model.Author;
import com.openBook.model.Book;
import com.openBook.test.config.builder.AuthorBuilder;
import com.openBook.test.config.builder.BookBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private static Book book1;
    private static Book book2;
    private static Author author;

    @BeforeAll
    static void beforeAll() {
        author = new AuthorBuilder()
                .withId(1L)
                .withFirstName("Example")
                .withMiddleName("Example")
                .withLastName("Example")
                .withNationality("Example")
                .withYearOfBirth(2024)
                .build();

        book1 = new BookBuilder()
                .withBookTitle("TestTitle")
                .withAuthor(author)
                .withGenre("Genre1")
                .build();

        book2 = new BookBuilder()
                .withBookTitle("TestTitle2")
                .withAuthor(author)
                .withGenre("Genre2")
                .build();
    }

    @Test
    void testSaveBook() {
        // Given Book and Author objects created as above
        // When
        Book savedBook = bookRepository.save(book1);

        // Then
        assertNotNull(savedBook);
        assertEquals(book1.getBookTitle(), savedBook.getBookTitle());
        assertEquals(book1.getAuthor(), savedBook.getAuthor());
    }

    @Test
    void testFindById() {
        // Given Book and Author objects created as above
        // When
        Book savedBook = bookRepository.save(book1);
        Optional<Book> foundBookOptional = bookRepository.findById(savedBook.getId());

        // Then
        assertTrue(foundBookOptional.isPresent());
        assertEquals(savedBook, foundBookOptional.get());
    }

    @Test
    void testFindAll() {
        // Given Book and Author objects created above
        bookRepository.save(book1);
        bookRepository.save(book2);

        // When
        List<Book> books = bookRepository.findAll();

        // Then
        assertEquals(2, books.size());
        assertTrue(books.contains(book1));
        assertTrue(books.contains(book2));
    }

    @Test
    void testDeleteBook() {
        // Given Book and Author objects created above
        Book savedBook = bookRepository.save(book1);

        // When
        bookRepository.delete(savedBook);

        // Then
        assertFalse(bookRepository.existsById(savedBook.getId()));
    }

    /**
     * New Code
     */
    @Test
    void testUpdateBook() {
        // Given
        Book savedBook = bookRepository.save(book1);

        // When
        savedBook.setBookTitle("UpdatedTitle");
        Book updatedBook = bookRepository.save(savedBook);

        // Then
        assertNotNull(updatedBook);
        assertEquals(savedBook.getId(), updatedBook.getId());
        assertEquals("UpdatedTitle", updatedBook.getBookTitle());
        assertEquals(savedBook.getAuthor(), updatedBook.getAuthor());
    }

    @Test
    void testFindByNonExistentId() {
        // Given
        Long nonExistentId = 999L;

        // When
        Optional<Book> foundBookOptional = bookRepository.findById(nonExistentId);

        // Then
        assertTrue(foundBookOptional.isEmpty());
    }

    @Test
    void testFindByTitle() {
        // Given
        bookRepository.save(book1);
        bookRepository.save(book2);
        String searchTitle = "TestTitle";

        // When
        List<Book> foundBooks = bookRepository.findByBookTitle(searchTitle);

        // Then
        assertEquals(1, foundBooks.size());
        assertEquals(searchTitle, foundBooks.get(0).getBookTitle());
    }

    @Test
    void testFindByGenre(){
        // Given
        bookRepository.save(book1);
        bookRepository.save(book2);
        String searchGenre = "Genre1";

        // When
        List<Book> foundBooks = bookRepository.findByGenre(searchGenre);

        // Then
        assertEquals(1, foundBooks.size());
        assertEquals(searchGenre, foundBooks.get(0).getGenre());
    }

    @Test
    void testDeleteNonExistentBook() {
        // Given
        Book nonExistentBook = new Book();

        // When
        bookRepository.delete(nonExistentBook);

        // Then
        // No exception should be thrown
    }

    @Test
    void testDeleteAllBooks() {
        // Given
        bookRepository.save(book1);
        bookRepository.save(book2);

        // When
        bookRepository.deleteAll();

        // Then
        assertEquals(0, bookRepository.count());
    }

    @Test
    void testFindByAuthor() {
        // Given
        bookRepository.save(book1);
        bookRepository.save(book2);

        // When
        List<Book> foundBooks = bookRepository.findByAuthor(author);

        // Then
        assertEquals(2, foundBooks.size());
        assertTrue(foundBooks.contains(book1));
        assertTrue(foundBooks.contains(book2));
    }
}
