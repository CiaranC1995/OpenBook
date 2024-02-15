package com.openBook.mapper;

import com.openBook.dto.BookDto;
import com.openBook.model.Author;
import com.openBook.model.Book;
import com.openBook.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BookMapper {

    //TODO FIX THIS AUTOWIRING, CAN'T MAP FROM DTO TO BOOK UNTIL IT IS
//    @Autowired
//    AuthorRepository authorRepository;

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "author", target = "authorName", qualifiedByName = "map")
    BookDto bookToBookDto(Book book);

    List<BookDto> booksToBookDtos(List<Book> books);

    @Named("map")
    default String map(Author author) {
        return author.getFirstName() + " " + author.getLastName();
    }

//    @Mapping(source = "authorName", target = "author", qualifiedByName = "mapDto")
//    Book bookDtoToBook(BookDto bookDto);

//    @Named("mapDto")
//    default Author map(String authorName) {
//        String[] names = authorName.split(" ");
//        if (names.length == 2) {
//            List<Author> existingAuthors = authorRepository.findByFirstNameAndLastName(names[0], names[1]);
//            if (existingAuthors.size() != 1) {
//                return null;
//            }
//            return existingAuthors.get(0);
//        } else {
//            return null;
//        }
//    }
}
