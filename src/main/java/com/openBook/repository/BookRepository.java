package com.openBook.repository;

import com.openBook.model.Author;
import com.openBook.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(Author author);
    List<Book> findByBookTitle(String bookTitle);
    List<Book> findByGenre(String genre);
}
