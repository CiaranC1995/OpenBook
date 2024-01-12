package com.openBook.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String bookName;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    private String publisher;
    private int yearOfPublish;
    @NotNull
    private String genre;

}