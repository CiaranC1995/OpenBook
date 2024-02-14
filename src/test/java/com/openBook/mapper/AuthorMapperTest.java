package com.openBook.mapper;

import com.openBook.dto.AuthorDto;
import com.openBook.model.Author;
import com.openBook.test.config.builder.AuthorBuilder;
import com.openBook.test.config.dtoBuilder.AuthorDtoBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuthorMapperTest {

    private static AuthorMapper mapper;

    private static Author testAuthorOne;
    private static Author testAuthorTwo;

    private static AuthorDto testAuthorDtoOne;
    private static AuthorDto testAuthorDtoTwo;

    @BeforeEach
    public void beforeEach() {
        mapper = Mappers.getMapper(AuthorMapper.class);
    }

    @BeforeAll
    public static void beforeAll() {
        testAuthorOne = new AuthorBuilder().build();
        testAuthorTwo = new AuthorBuilder().build();
        testAuthorDtoOne = new AuthorDtoBuilder().build();
        testAuthorDtoTwo = new AuthorDtoBuilder().build();
    }

    @Test
    public void testAuthorToAuthorDto() {
        // Given testAuthorOne

        // When
        AuthorDto authorDto = mapper.authorToAuthorDto(testAuthorOne);

        // Then
        assertEquals(testAuthorOne.getId(), authorDto.getId());
        assertEquals(testAuthorOne.getFirstName(), authorDto.getFirstName());
        assertEquals(testAuthorOne.getMiddleName(), authorDto.getMiddleName());
        assertEquals(testAuthorOne.getLastName(), authorDto.getLastName());
        assertEquals(testAuthorOne.getNationality(), authorDto.getNationality());
        assertEquals(testAuthorOne.getYearOfBirth(), authorDto.getYearOfBirth());

        assertNull(mapper.authorToAuthorDto(null));
    }

    @Test
    public void testAuthorDtoToAuthor() {
        // Given testAuthorDtoOne

        // When
        Author author = mapper.authorDtoToAuthor(testAuthorDtoOne);

        // Then
        assertEquals(testAuthorDtoOne.getId(), author.getId());
        assertEquals(testAuthorDtoOne.getFirstName(), author.getFirstName());
        assertEquals(testAuthorDtoOne.getMiddleName(), author.getMiddleName());
        assertEquals(testAuthorDtoOne.getLastName(), author.getLastName());
        assertEquals(testAuthorDtoOne.getNationality(), author.getNationality());
        assertEquals(testAuthorDtoOne.getYearOfBirth(), author.getYearOfBirth());

        assertNull(mapper.authorDtoToAuthor(null));
    }

    @Test
    public void testAuthorsToAuthorDtos() {
        // Given
        List<Author> authors = Arrays.asList(testAuthorOne, testAuthorTwo);

        // When
        List<AuthorDto> authorDtos = mapper.authorsToAuthorDtos(authors);

        // Then
        assertEquals(authors.size(), authorDtos.size());
        for (int i = 0; i < authors.size(); i++) {
            assertEquals(authors.get(i).getId(), authorDtos.get(i).getId());
            assertEquals(authors.get(i).getFirstName(), authorDtos.get(i).getFirstName());
            assertEquals(authors.get(i).getLastName(), authorDtos.get(i).getLastName());
            assertEquals(authors.get(i).getNationality(), authorDtos.get(i).getNationality());
            assertEquals(authors.get(i).getYearOfBirth(), authorDtos.get(i).getYearOfBirth());
        }
    }

    @Test
    public void testAuthorDtosToAuthors() {
        // Given
        List<AuthorDto> authorDtos = Arrays.asList(testAuthorDtoOne, testAuthorDtoTwo);

        // When
        List<Author> authors = mapper.authorDtosToAuthors(authorDtos);

        // Then
        assertEquals(authorDtos.size(), authors.size());
        for (int i = 0; i < authorDtos.size(); i++) {
            assertEquals(authorDtos.get(i).getId(), authors.get(i).getId());
            assertEquals(authorDtos.get(i).getFirstName(), authors.get(i).getFirstName());
            assertEquals(authorDtos.get(i).getLastName(), authors.get(i).getLastName());
            assertEquals(authorDtos.get(i).getNationality(), authors.get(i).getNationality());
            assertEquals(authorDtos.get(i).getYearOfBirth(), authors.get(i).getYearOfBirth());
        }
    }
}
