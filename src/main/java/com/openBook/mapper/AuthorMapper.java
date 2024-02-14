package com.openBook.mapper;

import com.openBook.dto.AuthorDto;
import com.openBook.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Mapping(target = "id", source = "author.id")
    @Mapping(target = "firstName", source = "author.firstName")
    @Mapping(target = "middleName", source = "author.middleName")
    @Mapping(target = "lastName", source = "author.lastName")
    @Mapping(target = "nationality", source = "author.nationality")
    @Mapping(target = "yearOfBirth", source = "author.yearOfBirth")
    AuthorDto authorToAuthorDto(Author author);

    List<AuthorDto> authorsToAuthorDtos(List<Author> authors);

    @Mapping(target = "id", source = "authorDto.id")
    @Mapping(target = "firstName", source = "authorDto.firstName")
    @Mapping(target = "middleName", source = "authorDto.middleName")
    @Mapping(target = "lastName", source = "authorDto.lastName")
    @Mapping(target = "nationality", source = "authorDto.nationality")
    @Mapping(target = "yearOfBirth", source = "authorDto.yearOfBirth")
    Author authorDtoToAuthor(AuthorDto authorDto);

    List<Author> authorDtosToAuthors(List<AuthorDto> authorDtos);
}