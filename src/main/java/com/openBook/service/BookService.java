package com.openBook.service;

import com.openBook.dto.AuthorDto;
import com.openBook.dto.BookDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public interface BookService {

    List<BookDto> getAllBooks();

    Optional<BookDto> getBookById(Long id);

    List<BookDto> getBooksByTitle(String title);

    List<BookDto> getBooksByAuthor(AuthorDto author);

    List<BookDto> getBooksByPublisher(String publisher);

    List<BookDto> getBooksByYearOfPublish(Integer yearOfPublish);

    List<BookDto> getBooksByGenre(String genre);

    BookDto createBook(@Valid BookDto bookDto);

    void deleteBook(Long id);

    void deleteAllBooks();

}