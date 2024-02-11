package com.openBook.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "author")
public class Author {

    @Id
    @NotNull
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    private String nationality;

    @NotNull
    @Column(name = "year_of_birth")
    private int yearOfBirth;
}
