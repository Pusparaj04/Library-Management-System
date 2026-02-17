package com.sb.BookManagement.service;

import com.sb.BookManagement.payload.BookDTO;
import com.sb.BookManagement.payload.BookResponse;
import org.hibernate.Internal;

import java.util.List;

public interface BookService {
    BookResponse getAllBooks(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    BookDTO addNewBook(BookDTO bookDTO);
    BookDTO updateBookDetails(Long bookId, BookDTO updatedBookDTO);
    BookDTO deleteBook(Long bookId);
    BookResponse getBookbyName(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String bookName);
    BookResponse getBookbyGenre(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String genre);
    void issueBook(Long bookId);
    void returnBook(Long bookId);
}
