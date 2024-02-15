package com.openBook.service;

import com.openBook.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();

    Optional<AuthorDto> getAuthorById(Long id);

    AuthorDto createAuthor(AuthorDto authorDto);

    AuthorDto updateAuthor(Long id, AuthorDto authorDto);

    void deleteAuthor(Long id);

    void deleteAllAuthors();
}