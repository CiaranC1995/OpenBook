package com.openBook.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @NotNull
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(name = "title")
    private String bookTitle;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    private String publisher;

    private int yearOfPublish;

    @NotBlank
    private String genre;
}