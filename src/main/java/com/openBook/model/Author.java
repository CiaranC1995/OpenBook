package com.openBook.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nationality;
    private int yearOfBirth;

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() { return middleName; }

    public String getLastName() {
        return lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id && Objects.equals(firstName, author.firstName)
                && Objects.equals(middleName, author.middleName) && Objects.equals(lastName, author.lastName)
                && Objects.equals(nationality, author.nationality) && Objects.equals(yearOfBirth, author.yearOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, middleName, lastName, nationality, yearOfBirth);
    }

    @Override
    public String toString() {
        return "Author(" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationality='" + nationality + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ')';
    }
}
