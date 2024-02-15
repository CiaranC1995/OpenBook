package com.openBook.service.implementation;

import com.openBook.dto.AuthorDto;
import com.openBook.mapper.AuthorMapper;
import com.openBook.model.Author;
import com.openBook.repository.AuthorRepository;
import com.openBook.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
                .map(author -> authorMapper.authorToAuthorDto(author))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorDto> getAuthorById(Long id) {
        return authorRepository.findById(id).map(author -> authorMapper.authorToAuthorDto(author));
    }

    @Override
    public AuthorDto createAuthor(AuthorDto authorDto) {
        return authorMapper.authorToAuthorDto(
                authorRepository.save(authorMapper.authorDtoToAuthor(authorDto)));
    }

    @Override
    public AuthorDto updateAuthor(Long id, AuthorDto authorDto) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        existingAuthor.setFirstName(authorDto.getFirstName());
        existingAuthor.setMiddleName(authorDto.getMiddleName());
        existingAuthor.setLastName(authorDto.getLastName());
        existingAuthor.setNationality(authorDto.getNationality());
        existingAuthor.setYearOfBirth(authorDto.getYearOfBirth());

        Author updatedAuthor = authorRepository.save(existingAuthor);

        return authorMapper.authorToAuthorDto(updatedAuthor);
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
