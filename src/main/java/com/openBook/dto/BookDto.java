package com.openBook.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String authorName;

    private String publisher;

    private Integer yearOfPublish;

    @NotNull
    private String genre;
}
