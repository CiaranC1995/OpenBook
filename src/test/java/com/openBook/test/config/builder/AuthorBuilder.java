package com.openBook.test.config.builder;

import com.openBook.model.Author;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class AuthorBuilder {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nationality;
    private Integer yearOfBirth;

    public AuthorBuilder() {
        id = new Random().nextLong();
        firstName = RandomStringUtils.randomAlphabetic(10);
        middleName = RandomStringUtils.randomAlphabetic(10);
        lastName = RandomStringUtils.randomAlphabetic(10);
        nationality = RandomStringUtils.randomAlphabetic(10);
        yearOfBirth = new Random().nextInt();
    }

    public Author build() {
        return new Author(id, firstName, middleName, lastName, nationality, yearOfBirth);
    }

    public AuthorBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AuthorBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AuthorBuilder withMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public AuthorBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AuthorBuilder withNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public AuthorBuilder withYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
        return this;
    }

}
