package com.openBook.test.config.dtoBuilder;

import com.openBook.dto.AuthorDto;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class AuthorDtoBuilder {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nationality;
    private Integer yearOfBirth;

    public AuthorDtoBuilder() {
        id = new Random().nextLong();
        firstName = RandomStringUtils.randomAlphabetic(10);
        middleName = RandomStringUtils.randomAlphabetic(10);
        lastName = RandomStringUtils.randomAlphabetic(10);
        nationality = RandomStringUtils.randomAlphabetic(10);
        yearOfBirth = new Random().nextInt();
    }

    public AuthorDto build() {
        return new AuthorDto(id, firstName, middleName, lastName, nationality, yearOfBirth);
    }

    public AuthorDtoBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AuthorDtoBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AuthorDtoBuilder withMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public AuthorDtoBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AuthorDtoBuilder withNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public AuthorDtoBuilder withYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
        return this;
    }

}
