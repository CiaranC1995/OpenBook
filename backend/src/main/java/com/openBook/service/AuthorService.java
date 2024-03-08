package com.openBook.service;

import com.openBook.dto.AuthorDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public interface AuthorService {

    List<AuthorDto> getAllAuthors();

    Optional<AuthorDto> getAuthorById(Long id);

    List<AuthorDto> getAuthorsByFirstName(String firstName);

    List<AuthorDto> getAuthorsByLastName(String lastName);

    List<AuthorDto> getAuthorsByFirstNameAndLastName(String firstName, String lastName);

    List<AuthorDto> getAuthorsByNationality(String nationality);

    AuthorDto createAuthor(@Valid AuthorDto authorDto);

    void deleteAuthor(Long id);

    void deleteAllAuthors();
}