package com.openBook.controller;

import com.openBook.dto.AuthorDto;
import com.openBook.dto.BookDto;
import com.openBook.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/books", "/books/"})
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    @RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.GET)
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto book = bookService.getBookById(id)
                .orElse(null);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @RequestMapping(value = {"/byTitle", "/byTitle/"}, method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getBooksByTitle(@RequestParam String title) {

        if (title == null) {
            return ResponseEntity.badRequest().build();
        }
        List<BookDto> books = bookService.getBooksByTitle(title);

        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    @RequestMapping(value = {"/byAuthor", "/byAuthor/"}, method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getBooksByAuthor(@RequestParam AuthorDto author) {

        if (author == null) {
            return ResponseEntity.badRequest().build();
        }
        List<BookDto> books = bookService.getBooksByAuthor(author);

        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    @RequestMapping(value = {"/byPublisher", "/byPublisher/"}, method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getBooksByPublisher(@RequestParam String publisher) {

        if (publisher == null) {
            return ResponseEntity.badRequest().build();
        }
        List<BookDto> books = bookService.getBooksByPublisher(publisher);

        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    @RequestMapping(value = {"/byPublishYear", "/byPublishYear/"}, method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getBooksByYearOfPublish(@RequestParam Integer yearOfPublish) {

        if (yearOfPublish == null) {
            return ResponseEntity.badRequest().build();
        }
        List<BookDto> books = bookService.getBooksByYearOfPublish(yearOfPublish);

        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    @RequestMapping(value = {"/byGenre", "/byGenre/"}, method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getBooksByGenre(@RequestParam String genre) {

        if (genre == null) {
            return ResponseEntity.badRequest().build();
        }
        List<BookDto> books = bookService.getBooksByGenre(genre);

        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createBook(@RequestBody @Valid BookDto bookDto) {
        try {
            BookDto createdBook = bookService.createBook(bookDto);
            return ResponseEntity.ok(String.format("Book with id=%s successfully created...", createdBook.getId()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = {"/delete/{id}", "/delete/{id}/"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok(String.format("Book with id=%s successfully deleted...", id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = {"/delete", "/delete/"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAllBooks() {
        bookService.deleteAllBooks();
        return ResponseEntity.ok().body("All books successfully deleted...");
    }
}
