package com.openBook.controller;

import com.openBook.dto.AuthorDto;
import com.openBook.service.AuthorService;
import com.openBook.test.config.dtoBuilder.AuthorDtoBuilder;
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
class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private final List<AuthorDto> testAuthors = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        testAuthors.add(new AuthorDtoBuilder().withId(1L).withFirstName("Ciarán").withLastName("Casey").withNationality("Irish").build());
        testAuthors.add(new AuthorDtoBuilder().withId(2L).withFirstName("Sarah").withLastName("Shine").withNationality("Irish").build());
    }

    @Test
    void testGetAllAuthors() {
        // Given
        when(authorService.getAllAuthors()).thenReturn(testAuthors);

        // When
        ResponseEntity<List<AuthorDto>> response = authorController.getAllAuthors();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testAuthors, response.getBody());
    }

    @Test
    void testGetAuthorByExistingId() {
        // Given
        Long authorId = 1L;
        AuthorDto expectedAuthor = testAuthors.get(0);

        when(authorService.getAuthorById(authorId)).thenReturn(Optional.of(expectedAuthor));

        // When
        ResponseEntity<AuthorDto> response = authorController.getAuthorById(authorId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedAuthor, response.getBody());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetAuthorByNonexistentId() {
        // Given
        Long authorId = 100L;
        when(authorService.getAuthorById(authorId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<AuthorDto> response = authorController.getAuthorById(authorId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetAuthorsByValidFirstName() {
        // Given
        String firstName = "Ciarán";
        when(authorService.getAuthorsByFirstName(firstName)).thenReturn(testAuthors);

        // When
        ResponseEntity<List<AuthorDto>> response = authorController.getAuthorsByName(firstName, null);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testAuthors, response.getBody());
    }

    @Test
    void testGetAuthorsByValidLastName() {
        // Given
        String lastName = "Shine";
        when(authorService.getAuthorsByLastName(lastName)).thenReturn(testAuthors.subList(1, 2));

        // When
        ResponseEntity<List<AuthorDto>> response = authorController.getAuthorsByName(null, lastName);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testAuthors.subList(1, 2), response.getBody());
    }

    @Test
    void testGetAuthorsByValidFirstNameAndLastName() {
        // Given
        String firstName = "Ciarán";
        String lastName = "Casey";
        when(authorService.getAuthorsByFirstNameAndLastName(firstName, lastName)).thenReturn(testAuthors.subList(0, 1));

        // When
        ResponseEntity<List<AuthorDto>> response = authorController.getAuthorsByName(firstName, lastName);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testAuthors.subList(0, 1), response.getBody());
    }

    @Test
    void testGetAuthorsByInvalidFirstNameAndLastName() {
        // Given
        String firstName = "Invalid";
        String lastName = "Author";
        when(authorService.getAuthorsByFirstNameAndLastName(firstName, lastName)).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<List<AuthorDto>> response = authorController.getAuthorsByName(firstName, lastName);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAuthorsByNameWithInvalidParameters() {
        // Given When
        ResponseEntity<List<AuthorDto>> response = authorController.getAuthorsByName(null, null);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetAuthorsByValidNationality() {
        // Given
        String nationality = "Irish";
        when(authorService.getAuthorsByNationality(nationality)).thenReturn(testAuthors);

        // When
        ResponseEntity<List<AuthorDto>> response = authorController.getAuthorsByNationality(nationality);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testAuthors, response.getBody());
    }

    @Test
    void testGetAuthorsByInvalidNationality() {
        // Given
        String nationality = "Unknown";
        when(authorService.getAuthorsByNationality(nationality)).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<List<AuthorDto>> response = authorController.getAuthorsByNationality(nationality);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateAuthorWithValidInputs() {
        // Given
        AuthorDto authorDto = new AuthorDtoBuilder().withId(3L).withFirstName("John").withLastName("Doe").withNationality("American").build();
        when(authorService.createAuthor(authorDto)).thenReturn(authorDto);

        // When
        ResponseEntity<String> response = authorController.createAuthor(authorDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Author with id=3 successfully created...", response.getBody());
    }

    @Test
    void testCreateAuthorWithInvalidInputs() {
        // Given
        AuthorDto authorDto = new AuthorDtoBuilder().withId(3L).withFirstName(null).withLastName("Doe").withNationality("American").build();
        String errorMessage = "Invalid input: firstName cannot be null.";
        when(authorService.createAuthor(authorDto)).thenThrow(new IllegalArgumentException(errorMessage));

        // When
        ResponseEntity<String> response = authorController.createAuthor(authorDto);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    void testDeleteAuthorWithExistingId() {
        // Given
        Long authorId = 1L;

        // When
        ResponseEntity<String> response = authorController.deleteAuthor(authorId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Author with id=1 successfully deleted...", response.getBody());
    }

    @Test
    void testDeleteAuthorWithNonexistentId() {
        // Given
        Long authorId = 100L;
        doThrow(EntityNotFoundException.class).when(authorService).deleteAuthor(authorId);

        // When
        ResponseEntity<String> response = authorController.deleteAuthor(authorId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteAllAuthors() {
        // Given When
        ResponseEntity<String> response = authorController.deleteAllAuthors();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("All authors successfully deleted...", response.getBody());
    }

}