package com.openBook.repository;

import com.openBook.model.Author;
import com.openBook.test.config.builder.AuthorBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    private static Author testAuthorOne;
    private static Author testAuthorTwo;

    @BeforeEach
    void beforeEach() {
        testAuthorOne = new AuthorBuilder().withFirstName("Ciarán").withLastName("Casey").withYearOfBirth(1995).build();
        testAuthorTwo = new AuthorBuilder().withNationality("Irish").build();
    }

    // create
    @Test
    void testSaveAuthor() {
        // Given testAuthorOne
        // When
        Author savedAuthor = authorRepository.save(testAuthorOne);

        // Then
        assertNotNull(savedAuthor);
        assertEquals(testAuthorOne.getId(), savedAuthor.getId());
    }

    // read
    @Test
    void testFindAuthorById() {
        // Given testAuthorOne
        // When
        Author savedAuthor = authorRepository.save(testAuthorOne);
        Optional<Author> foundAuthorOptional = authorRepository.findById(savedAuthor.getId());

        // Then
        assertTrue(foundAuthorOptional.isPresent());
        assertEquals(savedAuthor, foundAuthorOptional.get());
    }

    @Test
    void testFindAll() {
        // Given testAuthorOne and testAuthorTwo
        authorRepository.save(testAuthorOne);
        authorRepository.save(testAuthorTwo);

        // When
        List<Author> authors = authorRepository.findAll();

        // Then
        assertEquals(2, authors.size());
        assertTrue(authors.contains(testAuthorOne));
        assertTrue(authors.contains(testAuthorTwo));
    }

    @Test
    void testFindByNonexistentId() {
        // Given
        Long nonExistentId = 999L;

        // When
        Optional<Author> foundAuthorOptional = authorRepository.findById(nonExistentId);

        // Then
        assertTrue(foundAuthorOptional.isEmpty());
    }

    // find by last name
    @Test
    void testFindByLastName() {
        // Given
        authorRepository.save(testAuthorOne);
        authorRepository.save(testAuthorTwo);
        String searchLastName = "Casey";

        // When
        List<Author> foundAuthors = authorRepository.findByLastName(searchLastName);

        //Then
        assertEquals(1, foundAuthors.size());
        assertEquals(searchLastName, foundAuthors.get(0).getLastName());
    }

    @Test
    void testFindByFirstAndLastName() {
        // Given
        authorRepository.save(testAuthorOne);
        authorRepository.save(testAuthorTwo);
        String searchFirstName = "Ciarán";
        String searchLastName = "Casey";

        // When
        List<Author> foundAuthors = authorRepository.findByFirstNameAndLastName(searchFirstName, searchLastName);

        // Then
        assertEquals(1, foundAuthors.size());
        assertEquals(searchFirstName, foundAuthors.get(0).getFirstName());
        assertEquals(searchLastName, foundAuthors.get(0).getLastName());
    }

    // find by nationality
    @Test
    void testFindByNationality() {
        // Given
        authorRepository.save(testAuthorOne);
        authorRepository.save(testAuthorTwo);
        String searchNationality = "Irish";

        // When
        List<Author> foundAuthors = authorRepository.findByNationality(searchNationality);

        // Then
        assertEquals(1, foundAuthors.size());
        assertEquals(searchNationality, foundAuthors.get(0).getNationality());
    }

    // find by year of birth
    @Test
    void testFindByYearOfBirth() {
        // Given
        authorRepository.save(testAuthorOne);
        authorRepository.save(testAuthorTwo);
        int searchYearOfBirth = 1995;

        // When
        List<Author> foundAuthors = authorRepository.findByYearOfBirth(searchYearOfBirth);

        // Then
        assertEquals(1, foundAuthors.size());
        assertEquals(searchYearOfBirth, foundAuthors.get(0).getYearOfBirth());
    }

    // update
    @Test
    void testUpdateAuthor() {
        // Given
        Author savedAuthor = authorRepository.save(testAuthorOne);

        // When
        savedAuthor.setFirstName("Ciarán");
        Author updatedAuthor = authorRepository.save(savedAuthor);

        // Then
        assertNotNull(updatedAuthor);
        assertEquals(savedAuthor.getId(), updatedAuthor.getId());
        assertEquals("Ciarán", updatedAuthor.getFirstName());
        assertEquals(savedAuthor.getNationality(), updatedAuthor.getNationality());
    }

    // delete
    @Test
    void testDeleteAuthor() {
        // Given
        Author savedAuthor = authorRepository.save(testAuthorOne);

        // When
        authorRepository.delete(savedAuthor);

        // Then
        assertFalse(authorRepository.existsById(savedAuthor.getId()));
    }

    @Test
    void testDeleteAllAuthors() {
        // Given
        authorRepository.save(testAuthorOne);
        authorRepository.save(testAuthorTwo);

        // When
        authorRepository.deleteAll();

        // Then
        assertEquals(0, authorRepository.count());
    }

    @Test
    void testDeleteNonExistentAuthor() {
        // Given
        Author nonExistentAuthor = new Author();

        // When
        authorRepository.delete(nonExistentAuthor);

        // Then
        // No exception should be thrown
    }
}
