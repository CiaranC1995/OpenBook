package com.openBook.service.implementation;

import com.openBook.dto.AuthorDto;
import com.openBook.mapper.AuthorMapper;
import com.openBook.model.Author;
import com.openBook.repository.AuthorRepository;
import com.openBook.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::authorToAuthorDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorDto> getAuthorById(Long id) {
        return authorRepository.findById(id).map(authorMapper::authorToAuthorDto);
    }

    @Override
    public List<AuthorDto> getAuthorsByFirstName(String firstName) {
        return authorRepository.findByFirstName(firstName).stream()
                .map(authorMapper::authorToAuthorDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuthorDto> getAuthorsByLastName(String lastName) {
        return authorRepository.findByLastName(lastName).stream()
                .map(authorMapper::authorToAuthorDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuthorDto> getAuthorsByFirstNameAndLastName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName).stream()
                .map(authorMapper::authorToAuthorDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuthorDto> getAuthorsByNationality(String nationality) {
        return authorRepository.findByNationality(nationality).stream()
                .map(authorMapper::authorToAuthorDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto createAuthor(@Valid AuthorDto authorDto) throws IllegalArgumentException {
        if (authorRepository.existsById(authorDto.getId())) {
            throw new IllegalArgumentException(String.format("Author with id %s already exists.", authorDto.getId()));
        }
        return authorMapper.authorToAuthorDto(
                authorRepository.save(authorMapper.authorDtoToAuthor(authorDto))
        );
    }

    @Override
    public void deleteAuthor(Long id) throws EntityNotFoundException {
        if (!authorRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Author with id: %s not found...", id));
        }
        authorRepository.deleteById(id);
    }

    @Override
    public void deleteAllAuthors() {
        authorRepository.deleteAll();
    }
}
