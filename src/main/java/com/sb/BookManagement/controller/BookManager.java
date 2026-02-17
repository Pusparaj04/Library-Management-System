package com.sb.BookManagement.controller;

import com.sb.BookManagement.config.AppConstant;
import com.sb.BookManagement.payload.BookDTO;
import com.sb.BookManagement.payload.BookResponse;
import com.sb.BookManagement.service.BookService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

//@CrossOrigin(origins = "*") // used while testing with frontend
@RestController
@RequestMapping("/api/book")
public class BookManager {

    @Autowired
    private BookService bookService;
    public BookManager(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize(("hasAnyRole('USER', 'ADMIN')"))
    @GetMapping
    public ResponseEntity<BookResponse> getAllBooks(@RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                    @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                    @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BOOK_BY, required = false) String sortBy,
                                                    @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder){
        BookResponse bookResponse = bookService.getAllBooks(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @PreAuthorize(("hasAnyRole('USER', 'ADMIN')"))
    @GetMapping("/search/{bookName}")
    public ResponseEntity<BookResponse> getBookbyName(@RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                      @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                      @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BOOK_BY, required = false) String sortBy,
                                                      @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder,
                                                      @PathVariable String bookName){
        BookResponse bookResponse = bookService.getBookbyName(pageNumber, pageSize, sortBy, sortOrder, bookName);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @PreAuthorize(("hasAnyRole('USER', 'ADMIN')"))
    @GetMapping("/search/genre/{genre}")
    public ResponseEntity<BookResponse> getBookbyGenre(@RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                      @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                      @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BOOK_BY, required = false) String sortBy,
                                                      @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder,
                                                      @PathVariable String genre){
        BookResponse bookResponse = bookService.getBookbyGenre(pageNumber, pageSize, sortBy, sortOrder, genre);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @PreAuthorize(("hasRole('ADMIN')"))
    @PostMapping
    public ResponseEntity<BookDTO> addNewBook(@Valid @RequestBody BookDTO bookDTO){
        BookDTO addedBook = bookService.addNewBook(bookDTO);
        return new ResponseEntity<>(addedBook, HttpStatus.OK);
    }

    @PreAuthorize(("hasRole('ADMIN')"))
    @PutMapping("{bookId}")
    public ResponseEntity<BookDTO> updateBookDetails(@PathVariable Long bookId, @Valid @RequestBody BookDTO updatedBookDTO){
        BookDTO editedBookDTO = bookService.updateBookDetails(bookId, updatedBookDTO);
        return new ResponseEntity<>(editedBookDTO, HttpStatus.OK);
    }

    @PreAuthorize(("hasRole('ADMIN')"))
    @PutMapping("/issue/{bookId}")
    public ResponseEntity<String> issueBook(@PathVariable Long bookId){
        bookService.issueBook(bookId);
        return ResponseEntity.ok("Book with book id: "+bookId+" issued successfully.");
    }

    @PreAuthorize(("hasRole('ADMIN')"))
    @PutMapping("/return/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId){
        bookService.returnBook(bookId);
        return ResponseEntity.ok("Book with book id: "+bookId+" returned!!");
    }

    @PreAuthorize(("hasRole('ADMIN')"))
    @DeleteMapping("{bookId}")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable Long bookId){
        BookDTO deletedBook = bookService.deleteBook(bookId);
        return new ResponseEntity<>(deletedBook, HttpStatus.OK);
    }
}
