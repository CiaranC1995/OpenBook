package com.openBook.mapper;

import com.openBook.dto.BookDto;
import com.openBook.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "author.id", source = "authorId")
    BookDto bookToBookDto(Book book);

    List<BookDto> booksToBookDtos(List<Book> books);

    @Mapping(target = "authorId", source = "author.id")
    Book bookDtoToBook(BookDto bookDto);

    List<Book> bookDtosToBooks(List<BookDto> bookDtos);
}
