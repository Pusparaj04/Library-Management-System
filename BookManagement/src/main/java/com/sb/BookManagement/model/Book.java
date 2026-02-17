package com.sb.BookManagement.model;

import com.sb.BookManagement.validation.annotation.ValidPublishedYear;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    @NotBlank
    @Size(min = 2, message = "Book name should be of at least 5 character!!")
    private String bookName;
    @NotBlank
    @Size(min = 5, message = "Author name should be of at least 5 character!!")
    private String authorName;
    //this is custom annotation, created in validation package - if validation require for Time, DB
    // logic which might be dynamic or changing with time we need to create our custom annotation to validate it.
    @ValidPublishedYear
    private Integer publishedYear;
    @NotBlank
    @Size(min = 3, message = "Genre should be of at least 3 character!!")
    private String genre;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    @NotBlank
    private String condition;
}
