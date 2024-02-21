package com.openBook.service.implementation;

import com.openBook.dto.AuthorDto;
import com.openBook.dto.BookDto;
import com.openBook.mapper.AuthorMapper;
import com.openBook.mapper.BMapper;
import com.openBook.repository.BookRepository;
import com.openBook.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BMapper bookMapper;
    private final AuthorMapper authorMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BMapper bookMapper, AuthorMapper authorMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::bookToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookDto> getBookById(Long id) {
        return bookRepository.findById(id).map(bookMapper::bookToBookDto);
    }

    @Override
    public List<BookDto> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title).stream()
                .map(bookMapper::bookToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getBooksByAuthor(AuthorDto authorDto) {
        return bookRepository.findByAuthor(authorMapper.authorDtoToAuthor(authorDto))
                .stream()
                .map(bookMapper::bookToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getBooksByPublisher(String publisher) {
        return bookRepository.findByPublisher(publisher).stream()
                .map(bookMapper::bookToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getBooksByYearOfPublish(Integer yearOfPublish) {
        return bookRepository.findByYearOfPublish(yearOfPublish).stream()
                .map(bookMapper::bookToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre).stream()
                .map(bookMapper::bookToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto createBook(@Valid BookDto bookDto) {
        return bookMapper.bookToBookDto(
                bookRepository.save(bookMapper.bookDtoToBook(bookDto))
        );
    }

    @Override
    public void deleteBook(Long id) throws EntityNotFoundException {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Book with id: %s not found...", id));
        }
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }

}