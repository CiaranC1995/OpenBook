package com.openBook.service;

import com.openBook.model.Book;
import com.openBook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // find a specific book
    public Optional<Book> getBookById(long id) {
        return Optional.of(bookRepository.getReferenceById(id));
    }

    // create a book
    public void addBook(Book book) {
        bookRepository.save(book);
    }
    // delete a book

    // modify a book
}
