package com.openBook.repository;

import com.openBook.model.Author;
import com.openBook.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(Author author);
    List<Book> findByBookTitle(String bookTitle);
    List<Book> findByGenre(String genre);
}
