package com.openBook.controller;

import com.openBook.dto.AuthorDto;
import com.openBook.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/authors", "/authors/"})
public class AuthorController {

    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorDto> authors = authorService.getAllAuthors();
        if (authors.isEmpty()) {
            log.info("No authors exist ...");
            return ResponseEntity.notFound().build();
        }
        log.info("Returning {} author(s) ...", authors.size());
        return ResponseEntity.ok(authors);
    }

    @RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.GET)
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        AuthorDto author = authorService.getAuthorById(id)
                .orElse(null);
        if (author == null) {
            log.info("No author with id={} exists ...", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Returning author with id={} ...", id);
        return ResponseEntity.ok(author);
    }

    @RequestMapping(value = {"/name", "/name/"}, method = RequestMethod.GET)
    public ResponseEntity<List<AuthorDto>> getAuthorsByName(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {

        List<AuthorDto> authors;

        if (firstName != null && lastName != null) {
            log.info("Searching for author(s) named firstName={} lastName={} ...", firstName, lastName);
            authors = authorService.getAuthorsByFirstNameAndLastName(firstName, lastName);
        } else if (firstName != null) {
            log.info("Searching for author(s) named firstName={} ...", firstName);
            authors = authorService.getAuthorsByFirstName(firstName);
        } else if (lastName != null) {
            log.info("Searching for author(s) named lastName={} ...", lastName);
            authors = authorService.getAuthorsByLastName(lastName);
        } else {
            log.warn("Bad request, could not process request parameter(s) ...");
            return ResponseEntity.badRequest().build();
        }
        if (authors.isEmpty()) {
            log.info("No matching author(s) exist ...");
            return ResponseEntity.notFound().build();
        }
        log.info("Returning {} author(s) ...", authors.size());
        return ResponseEntity.ok(authors);
    }

    @RequestMapping(value = {"/nationality", "/nationality/"}, method = RequestMethod.GET)
    public ResponseEntity<List<AuthorDto>> getAuthorsByNationality(@RequestParam String nationality) {

        List<AuthorDto> authors = authorService.getAuthorsByNationality(nationality);

        if (authors.isEmpty()) {
            log.info("No author(s) with nationality={} exist ...", nationality);
            return ResponseEntity.notFound().build();
        }
        log.info("Returning {} author(s) ...", authors.size());
        return ResponseEntity.ok(authors);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createAuthor(@RequestBody @Valid AuthorDto authorDto) {
        try {
            log.info("Attempting to create author with id={} ...", authorDto.getId());
            AuthorDto createdAuthor = authorService.createAuthor(authorDto);
            log.info("Author with with id={} successfully created ...", createdAuthor.getId());
            return ResponseEntity.ok(String.format("Author with id=%s successfully created ...", createdAuthor.getId()));
        } catch (IllegalArgumentException e) {
            log.warn("Failed to create author with id={} ...", authorDto.getId());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = {"/delete/{id}", "/delete/{id}/"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        try {
            log.info("Attempting to delete author with id={} ...", id);
            authorService.deleteAuthor(id);
            log.info("Successfully deleted author with id={} ...", id);
            return ResponseEntity.ok().body(String.format("Author with id=%s successfully deleted ...", id));
        } catch (EntityNotFoundException e) {
            log.warn("Deletion attempt unsuccessful, no author with id={} exists ...", id);
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = {"/delete", "/delete/"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAllAuthors() {
        log.info("Attempting to delete all authors ...");
        authorService.deleteAllAuthors();
        log.info("All authors successfully deleted ...");
        return ResponseEntity.ok().body("All authors successfully deleted ...");
    }
}
