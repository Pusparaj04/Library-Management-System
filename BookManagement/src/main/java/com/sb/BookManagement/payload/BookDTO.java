package com.sb.BookManagement.payload;

import com.sb.BookManagement.model.BookStatus;
import com.sb.BookManagement.validation.annotation.ValidPublishedYear;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long bookId;
    @NotBlank
    @Size(min = 2, message = "Book name should be of at least 5 character!!")
    private String bookName;
    @NotBlank
    @Size(min = 5, message = "Author name should be of at least 5 character!!")
    private String authorName;
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
