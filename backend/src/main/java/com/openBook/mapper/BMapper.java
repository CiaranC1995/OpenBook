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

@Component
@Mapper(componentModel = "spring", uses = AuthorRepository.class)
public abstract class BMapper {

    @Autowired
    protected AuthorRepository authorRepository;

    public static final BMapper INSTANCE = Mappers.getMapper(BMapper.class);

    @Mapping(source = "author", target = "authorName", qualifiedByName = "map")
    public abstract BookDto bookToBookDto(Book book);

    public abstract List<BookDto> booksToBookDtos(List<Book> books);

    @Named("map")
    protected String map(Author author) {
        return author.getFirstName() + " " + author.getLastName();
    }

    @Mapping(source = "authorName", target = "author", qualifiedByName = "mapDto")
    public abstract Book bookDtoToBook(BookDto bookDto);

    @Named("mapDto")
    protected Author mapDto(String authorName) {
        String[] names = authorName.split(" ");
        if (names.length == 2) {
            List<Author> existingAuthors = authorRepository.findByFirstNameAndLastName(names[0], names[1]);
            if (existingAuthors.size() != 1) {
                return null;
            }
            return existingAuthors.get(0);
        } else {
            return null;
        }
    }
}
