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

    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    public void updateBook(Book book) throws IllegalArgumentException {
        Optional<Book> existingBookOptional = bookRepository.findById(book.getId());
        if (existingBookOptional.isEmpty()) {
            throw new IllegalArgumentException("Book with ID " + book.getId() + " does not exist.");
        }

        if (book.getBookTitle() == null || book.getBookTitle().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be empty.");
        }

        if (book.getAuthor() == null) {
            throw new IllegalArgumentException("Book must have an author.");
        }

        bookRepository.save(book);
    }
}