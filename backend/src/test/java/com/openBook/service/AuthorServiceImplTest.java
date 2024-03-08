package com.openBook.service;

import com.openBook.dto.AuthorDto;
import com.openBook.mapper.AuthorMapper;
import com.openBook.model.Author;
import com.openBook.repository.AuthorRepository;
import com.openBook.service.implementation.AuthorServiceImpl;
import com.openBook.test.config.builder.AuthorBuilder;
import com.openBook.test.config.dtoBuilder.AuthorDtoBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAuthors() {
        // Given
        when(authorRepository.findAll()).thenReturn(Arrays.asList(new Author(), new Author()));
        when(authorMapper.authorToAuthorDto(any(Author.class))).thenReturn(new AuthorDto());

        // When
        List<AuthorDto> authors = authorService.getAllAuthors();

        // Then
        assertEquals(2, authors.size());
        verify(authorRepository, times(1)).findAll();
        verify(authorMapper, times(2)).authorToAuthorDto(any(Author.class));
    }

    @Test
    void testGetAuthorByIdThatExists() {
        // Given
        Author author = new AuthorBuilder().withId(1L).build();
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorMapper.authorToAuthorDto(author)).thenReturn(new AuthorDto());

        // When
        Optional<AuthorDto> optionalAuthorDto = authorService.getAuthorById(1L);

        // Then
        assertTrue(optionalAuthorDto.isPresent());
        verify(authorRepository, times(1)).findById(1L);
        verify(authorMapper, times(1)).authorToAuthorDto(author);
    }

    @Test
    void testGetAuthorByIdThatDoesNotExists() {
        // Given
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<AuthorDto> optionalAuthorDto = authorService.getAuthorById(1L);

        // Then
        assertFalse(optionalAuthorDto.isPresent());
        verify(authorRepository, times(1)).findById(1L);
        verify(authorMapper, never()).authorToAuthorDto(any(Author.class));
    }

    @Test
    void testCreateAuthorWithValidAuthorDto() {
        // Given
        AuthorDto authorDto = new AuthorDtoBuilder().withId(1L).build();
        when(authorRepository.existsById(authorDto.getId())).thenReturn(false);
        Author author = authorMapper.authorDtoToAuthor(authorDto);
        when(authorMapper.authorDtoToAuthor(authorDto)).thenReturn(author);
        when(authorMapper.authorToAuthorDto(author)).thenReturn(authorDto);

        // When
        AuthorDto createdAuthorDto = authorService.createAuthor(authorDto);

        // Then
        assertEquals(authorDto, createdAuthorDto);
        verify(authorRepository, times(1)).existsById(1L);
        verify(authorRepository, times(1)).save(author);
        verify(authorMapper, times(2)).authorDtoToAuthor(authorDto);
        verify(authorMapper, times(1)).authorToAuthorDto(author);
    }

    @Test
    void testCreateAuthorWithDuplicateId() {
        // Given
        AuthorDto authorDto = new AuthorDtoBuilder().withId(1L).build();
        when(authorRepository.existsById(1L)).thenReturn(true);

        // When Then
        assertThrows(IllegalArgumentException.class, () -> authorService.createAuthor(authorDto));
        verify(authorRepository, times(1)).existsById(1L);
        verify(authorRepository, never()).save(any(Author.class));
        verify(authorMapper, never()).authorDtoToAuthor(any(AuthorDto.class));
        verify(authorMapper, never()).authorToAuthorDto(any(Author.class));
    }

    @Test
    void testDeleteExistingAuthor() {
        // Given
        when(authorRepository.existsById(1L)).thenReturn(true);

        // When
        assertDoesNotThrow(() -> authorService.deleteAuthor(1L));

        // Then
        verify(authorRepository, times(1)).existsById(1L);
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNonexistentAuthor() {
        // Given
        when(authorRepository.existsById(1L)).thenReturn(false);

        // When Then
        assertThrows(EntityNotFoundException.class, () -> authorService.deleteAuthor(1L));
        verify(authorRepository, times(1)).existsById(1L);
        verify(authorRepository, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteAllAuthors() {
        // When
        authorService.deleteAllAuthors();

        // Then
        verify(authorRepository, times(1)).deleteAll();
    }

    @Test
    void testGetAuthorByFirstName() {
        // Given
        String firstName = "John";
        Author author = new AuthorBuilder().withFirstName(firstName).build();
        when(authorRepository.findByFirstName(firstName)).thenReturn(Collections.singletonList(author));

        AuthorDto authorDto = authorMapper.authorToAuthorDto(author);
        when(authorMapper.authorToAuthorDto(author)).thenReturn(authorDto);

        // When
        List<AuthorDto> authors = authorService.getAuthorsByFirstName(firstName);

        // Then
        assertThat(authors).containsExactly(authorDto);
        verify(authorRepository, times(1)).findByFirstName(firstName);
        verify(authorMapper, times(2)).authorToAuthorDto(author);
    }

    @Test
    void testGetMultipleAuthorsWithSameFirstName() {
        // Given
        String firstName = "Ciarán";
        Author authorOne = new AuthorBuilder().withFirstName(firstName).build();
        Author authorTwo = new AuthorBuilder().withFirstName(firstName).build();
        when(authorRepository.findByFirstName(firstName)).thenReturn(Arrays.asList(authorOne, authorTwo));

        AuthorDto authorDtoOne = authorMapper.authorToAuthorDto(authorOne);
        AuthorDto authorDtoTwo = authorMapper.authorToAuthorDto(authorTwo);
        when(authorMapper.authorToAuthorDto(authorOne)).thenReturn(authorDtoOne);
        when(authorMapper.authorToAuthorDto(authorTwo)).thenReturn(authorDtoTwo);

        // When
        List<AuthorDto> authors = authorService.getAuthorsByFirstName(firstName);

        // Then
        assertThat(authors).containsExactly(authorDtoOne, authorDtoTwo);
        verify(authorRepository, times(1)).findByFirstName(firstName);
        verify(authorMapper, times(2)).authorToAuthorDto(authorOne);
        verify(authorMapper, times(2)).authorToAuthorDto(authorTwo);
    }

    @Test
    void testGetAuthorByFirstNameAndLastName() {
        // Given
        String firstName = "Ciarán";
        String lastName = "Casey";
        Author author = new AuthorBuilder().withFirstName(firstName).withLastName(lastName).build();
        when(authorRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(Collections.singletonList(author));

        AuthorDto authorDto = authorMapper.authorToAuthorDto(author);
        when(authorMapper.authorToAuthorDto(author)).thenReturn(authorDto);

        // When
        List<AuthorDto> authors = authorService.getAuthorsByFirstNameAndLastName(firstName, lastName);

        // Then
        assertThat(authors).containsExactly(authorDto);
        verify(authorRepository, times(1)).findByFirstNameAndLastName(firstName, lastName);
        verify(authorMapper, times(2)).authorToAuthorDto(author);
    }

    @Test
    void testGetMultipleAuthorsWithSameFirstNameAndLastName() {
        // Given
        String firstName = "Ciarán";
        String lastName = "Casey";
        Author authorOne = new AuthorBuilder().withFirstName(firstName).withLastName(lastName).build();
        Author authorTwo = new AuthorBuilder().withFirstName(firstName).withLastName(lastName).build();
        when(authorRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(Arrays.asList(authorOne, authorTwo));

        AuthorDto authorDtoOne = authorMapper.authorToAuthorDto(authorOne);
        AuthorDto authorDtoTwo = authorMapper.authorToAuthorDto(authorTwo);
        when(authorMapper.authorToAuthorDto(authorOne)).thenReturn(authorDtoOne);
        when(authorMapper.authorToAuthorDto(authorTwo)).thenReturn(authorDtoTwo);

        // When
        List<AuthorDto> authors = authorService.getAuthorsByFirstNameAndLastName(firstName, lastName);

        // Then
        assertThat(authors).containsExactly(authorDtoOne, authorDtoTwo);
        verify(authorRepository, times(1)).findByFirstNameAndLastName(firstName, lastName);
        verify(authorMapper, times(2)).authorToAuthorDto(authorOne);
        verify(authorMapper, times(2)).authorToAuthorDto(authorTwo);
    }

    @Test
    void testGetMultipleAuthorsWithSameNationality() {
        // Given
        String nationality = "Irish";
        Author authorOne = new AuthorBuilder().withNationality(nationality).build();
        Author authorTwo = new AuthorBuilder().withNationality(nationality).build();
        when(authorRepository.findByNationality(nationality)).thenReturn(Arrays.asList(authorOne, authorTwo));

        AuthorDto authorDtoOne = authorMapper.authorToAuthorDto(authorOne);
        AuthorDto authorDtoTwo = authorMapper.authorToAuthorDto(authorTwo);
        when(authorMapper.authorToAuthorDto(authorOne)).thenReturn(authorDtoOne);
        when(authorMapper.authorToAuthorDto(authorTwo)).thenReturn(authorDtoTwo);

        // When
        List<AuthorDto> authors = authorService.getAuthorsByNationality(nationality);

        // Then
        assertThat(authors).containsExactly(authorDtoOne, authorDtoTwo);
        verify(authorRepository, times(1)).findByNationality(nationality);
        verify(authorMapper, times(2)).authorToAuthorDto(authorOne);
        verify(authorMapper, times(2)).authorToAuthorDto(authorTwo);
    }

    @Test
    void testGetAuthorByNationality() {
        // Given
        String nationality = "Irish";
        Author author = new AuthorBuilder().withNationality(nationality).build();
        when(authorRepository.findByNationality(nationality)).thenReturn(Collections.singletonList(author));

        AuthorDto authorDto = authorMapper.authorToAuthorDto(author);
        when(authorMapper.authorToAuthorDto(author)).thenReturn(authorDto);

        // When
        List<AuthorDto> authors = authorService.getAuthorsByNationality(nationality);

        // Then
        assertThat(authors).containsExactly(authorDto);
        verify(authorRepository, times(1)).findByNationality(nationality);
        verify(authorMapper, times(2)).authorToAuthorDto(author);
    }

    @Test
    void testGetAuthorByLastName() {
        // Given
        String lastName = "Casey";
        Author author = new AuthorBuilder().withLastName(lastName).build();
        when(authorRepository.findByLastName(lastName)).thenReturn(Collections.singletonList(author));

        AuthorDto authorDto = authorMapper.authorToAuthorDto(author);
        when(authorMapper.authorToAuthorDto(author)).thenReturn(authorDto);

        // When
        List<AuthorDto> authors = authorService.getAuthorsByLastName(lastName);

        // Then
        assertThat(authors).containsExactly(authorDto);
        verify(authorRepository, times(1)).findByLastName(lastName);
        verify(authorMapper, times(2)).authorToAuthorDto(author);
    }

    @Test
    void testGetMultipleAuthorsWithSameLastName() {
        // Given
        String lastName = "Casey";
        Author authorOne = new AuthorBuilder().withLastName(lastName).build();
        Author authorTwo = new AuthorBuilder().withLastName(lastName).build();
        when(authorRepository.findByLastName(lastName)).thenReturn(Arrays.asList(authorOne, authorTwo));

        AuthorDto authorDtoOne = authorMapper.authorToAuthorDto(authorOne);
        AuthorDto authorDtoTwo = authorMapper.authorToAuthorDto(authorTwo);
        when(authorMapper.authorToAuthorDto(authorOne)).thenReturn(authorDtoOne);
        when(authorMapper.authorToAuthorDto(authorTwo)).thenReturn(authorDtoTwo);

        // When
        List<AuthorDto> authors = authorService.getAuthorsByLastName(lastName);

        // Then
        assertThat(authors).containsExactly(authorDtoOne, authorDtoTwo);
        verify(authorRepository, times(1)).findByLastName(lastName);
        verify(authorMapper, times(2)).authorToAuthorDto(authorOne);
        verify(authorMapper, times(2)).authorToAuthorDto(authorTwo);
    }
}