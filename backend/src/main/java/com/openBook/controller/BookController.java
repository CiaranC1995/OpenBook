package com.openBook.controller;

import com.openBook.dto.AuthorDto;
import com.openBook.dto.BookDto;
import com.openBook.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/books", "/books/"})
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            log.info("No books exist ...");
            return ResponseEntity.notFound().build();
        }
        log.info("Returning {} book(s) ...", books.size());
        return ResponseEntity.ok(books);
    }

    @RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.GET)
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto book = bookService.getBookById(id)
                .orElse(null);
        if (book == null) {
            log.info("No book with id={} exists ...", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Returning book with id={} ...", id);
        return ResponseEntity.ok(book);
    }

    @RequestMapping(value = {"/byTitle", "/byTitle/"}, method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getBooksByTitle(@RequestParam String title) {

        if (title == null) {
            log.warn("Bad request, could not process request parameter(s) ...");
            return ResponseEntity.badRequest().build();
        }
        List<BookDto> books = bookService.getBooksByTitle(title);

        if (books.isEmpty()) {
            log.info("No book(s) with title={} exist ...", title);
            return ResponseEntity.notFound().build();
        }
        log.info("Returning {} book(s) ...", books.size());
        return ResponseEntity.ok(books);
    }

    @RequestMapping(value = {"/byAuthor", "/byAuthor/"}, method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getBooksByAuthor(@RequestParam AuthorDto author) {

        if (author == null) {
            log.warn("Bad request, could not process request parameter(s) ...");
            return ResponseEntity.badRequest().build();
        }
        List<BookDto> books = bookService.getBooksByAuthor(author);

        if (books.isEmpty()) {
            log.info("No book(s) by author={} exist ...", author);
            return ResponseEntity.notFound().build();
        }
        log.info("Returning {} book(s) ...", books.size());
        return ResponseEntity.ok(books);
    }

    @RequestMapping(value = {"/byPublisher", "/byPublisher/"}, method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getBooksByPublisher(@RequestParam String publisher) {

        if (publisher == null) {
            log.warn("Bad request, could not process request parameter(s) ...");
            return ResponseEntity.badRequest().build();
        }
        List<BookDto> books = bookService.getBooksByPublisher(publisher);

        if (books.isEmpty()) {
            log.info("No book(s) by publisher={} exist ...", publisher);
            return ResponseEntity.notFound().build();
        }
        log.info("Returning {} book(s) ...", books.size());
        return ResponseEntity.ok(books);
    }

    @RequestMapping(value = {"/byPublishYear", "/byPublishYear/"}, method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getBooksByYearOfPublish(@RequestParam Integer yearOfPublish) {

        if (yearOfPublish == null) {
            log.warn("Bad request, could not process request parameter(s) ...");
            return ResponseEntity.badRequest().build();
        }
        List<BookDto> books = bookService.getBooksByYearOfPublish(yearOfPublish);

        if (books.isEmpty()) {
            log.info("No book(s) published in year={} exist ...", yearOfPublish);
            return ResponseEntity.notFound().build();
        }
        log.info("Returning {} book(s) ...", books.size());
        return ResponseEntity.ok(books);
    }

    @RequestMapping(value = {"/byGenre", "/byGenre/"}, method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getBooksByGenre(@RequestParam String genre) {

        if (genre == null) {
            log.warn("Bad request, could not process request parameter(s) ...");
            return ResponseEntity.badRequest().build();
        }
        List<BookDto> books = bookService.getBooksByGenre(genre);

        if (books.isEmpty()) {
            log.info("No book(s) in genre={} exist ...", genre);
            return ResponseEntity.notFound().build();
        }
        log.info("Returning {} book(s) ...", books.size());
        return ResponseEntity.ok(books);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createBook(@RequestBody @Valid BookDto bookDto) {
        try {
            log.info("Attempting to create book with id={} ...", bookDto.getId());
            BookDto createdBook = bookService.createBook(bookDto);
            log.info("Book with with id={} successfully created ...", createdBook.getId());
            return ResponseEntity.ok(String.format("Book with id=%s successfully created ...", createdBook.getId()));
        } catch (IllegalArgumentException e) {
            log.warn("Failed to create book with id={} ...", bookDto.getId());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = {"/delete/{id}", "/delete/{id}/"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
            log.info("Attempting to delete book with id={} ...", id);
            bookService.deleteBook(id);
            log.info("Successfully deleted book with id={} ...", id);
            return ResponseEntity.ok(String.format("Book with id=%s successfully deleted ...", id));
        } catch (EntityNotFoundException e) {
            log.warn("Deletion attempt unsuccessful, no book with id={} exists ...", id);
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = {"/delete", "/delete/"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAllBooks() {
        log.info("Attempting to delete all books ...");
        bookService.deleteAllBooks();
        log.info("All books successfully deleted ...");
        return ResponseEntity.ok().body("All books successfully deleted ...");
    }
}
