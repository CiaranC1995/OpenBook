package com.openBook.service;

import com.openBook.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {

    List<Book> getAllBooks();

    Optional<Book> getBookById(long id);

    void addBook(Book book);

    void deleteBookById(long id);

    void updateBook(Book book);
}