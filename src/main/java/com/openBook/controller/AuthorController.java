package com.openBook.controller;

import com.openBook.dto.AuthorDto;
import com.openBook.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping(value = {"/authors", "/authors/"}, method = RequestMethod.GET)
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorDto> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @RequestMapping(value = {"/authors/{id}", "/authors/{id}/"}, method = RequestMethod.GET)
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        AuthorDto author = authorService.getAuthorById(id)
                .orElse(null);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(author);
    }

    @RequestMapping(value = "/authors/name", method = RequestMethod.GET)
    public ResponseEntity<List<AuthorDto>> getAuthorsByName(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {

        List<AuthorDto> authors;

        if (firstName != null && lastName != null) {
            authors = authorService.getAuthorsByFirstNameAndLastName(firstName, lastName);
        } else if (firstName != null) {
            authors = authorService.getAuthorsByFirstName(firstName);
        } else if (lastName != null) {
            authors = authorService.getAuthorsByLastName(lastName);
        } else {
            return ResponseEntity.badRequest().build();
        }
        if (authors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authors);
    }

    @RequestMapping(value = "/authors/nationality", method = RequestMethod.GET)
    public ResponseEntity<List<AuthorDto>> getAuthorsByNationality(
            @RequestParam(required = true) String nationality) {

        List<AuthorDto> authors = authorService.getAuthorsByNationality(nationality);

        if (authors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authors);
    }

    @RequestMapping(value = {"/authors", "/authors/"}, method = RequestMethod.POST)
    public ResponseEntity<String> createAuthor(@RequestBody @Valid AuthorDto authorDto) {
        try {
            AuthorDto createdAuthor = authorService.createAuthor(authorDto);
            return ResponseEntity.ok(String.format("Author with id=%s successfully created...", authorDto.getId()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = {"/authors/delete/{id}", "/authors/delete/{id}/"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.ok().body(String.format("Author with id=%s successfully deleted...", id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = {"/authors/delete", "/authors/delete/"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAllAuthors() {
        authorService.deleteAllAuthors();
        return ResponseEntity.ok().body("All authors successfully deleted...");
    }
}