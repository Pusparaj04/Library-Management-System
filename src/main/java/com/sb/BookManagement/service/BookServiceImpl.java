package com.sb.BookManagement.service;

import com.sb.BookManagement.exceptions.APIException;
import com.sb.BookManagement.exceptions.ResourceNotFoundException;
import com.sb.BookManagement.model.Book;
import com.sb.BookManagement.model.BookStatus;
import com.sb.BookManagement.payload.BookDTO;
import com.sb.BookManagement.payload.BookResponse;
import com.sb.BookManagement.repositories.BookRepository;
import org.hibernate.Internal;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service //bean -> object of this class is to be handled by Spring
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BookResponse getAllBooks(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending() :   //if sortOrder == asc then ascending else descending => ternary operator
                Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Book> bookPage = bookRepository.findAll(pageDetails);
        List<Book> allBooks = bookPage.getContent();
        if(allBooks.isEmpty()){
            throw new APIException("No any book available!!");
        }
        List<BookDTO> bookDTOS = allBooks.stream().map(book -> modelMapper
                .map(book, BookDTO.class)).collect(Collectors.toList());

        BookResponse bookResponse = new BookResponse();
        bookResponse.setContent(bookDTOS);
        bookResponse.setPageNumber(bookPage.getNumber());
        bookResponse.setPageSize(bookPage.getSize());
        bookResponse.setTotalElements(bookPage.getTotalElements());
        bookResponse.setTotalPages(bookPage.getTotalPages());
        bookResponse.setLastPage(bookPage.isLast());
        return bookResponse;
    }

    @Override
    public BookDTO addNewBook(BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        book.setStatus(BookStatus.AVAILABLE);
        Book newBook = bookRepository.save(book);
        BookDTO savedBook = modelMapper.map(newBook, BookDTO.class);
        return savedBook;
    }

    @Override
    public BookDTO updateBookDetails(Long bookId, BookDTO updatedBookDTO) {
        Book book = modelMapper.map(updatedBookDTO, Book.class);
        String bName = book.getBookName();
        Book savedBook = bookRepository.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Book", "bookId", bookId));
        book.setBookId(bookId);
        Book newbook = bookRepository.save(book);
        newbook.setStatus(BookStatus.AVAILABLE);
        BookDTO savedBookDTO = modelMapper.map(newbook, BookDTO.class);
        return savedBookDTO;
    }

    @Override
    public BookDTO deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Book", "bookId", bookId));
        bookRepository.delete(book);
        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    public BookResponse getBookbyName(Integer pageNumber, Integer pageSize,
                                      String sortBy, String sortOrder, String bookName) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending() :   //if sortOrder == asc then ascending else descending => ternary operator
                Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Book> bookPage = bookRepository.findByBookNameContainingIgnoreCase(bookName, pageDetails);
        List<Book> allBooks = bookPage.getContent();
        if(allBooks.isEmpty()){
            throw new ResourceNotFoundException("Book", "bookName", bookName);
        }
        List<BookDTO> bookDTOS = allBooks.stream().map(book -> modelMapper
                .map(book, BookDTO.class)).collect(Collectors.toList());

        BookResponse bookResponse = new BookResponse();
        bookResponse.setContent(bookDTOS);
        bookResponse.setPageNumber(bookPage.getNumber());
        bookResponse.setPageSize(bookPage.getSize());
        bookResponse.setTotalElements(bookPage.getTotalElements());
        bookResponse.setTotalPages(bookPage.getTotalPages());
        bookResponse.setLastPage(bookPage.isLast());
        return bookResponse;
    }

    @Override
    public BookResponse getBookbyGenre(Integer pageNumber, Integer pageSize, String sortBy,
                                       String sortOrder, String genre) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending() :   //if sortOrder == asc then ascending else descending => ternary operator
                Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Book> bookPage = bookRepository.findByGenreContainingIgnoreCase(genre, pageDetails);
        List<Book> allBooks = bookPage.getContent();
        if(allBooks.isEmpty()){
            throw new ResourceNotFoundException("Book", "genre", genre);
        }
        List<BookDTO> bookDTOS = allBooks.stream().map(book -> modelMapper
                .map(book, BookDTO.class)).collect(Collectors.toList());

        BookResponse bookResponse = new BookResponse();
        bookResponse.setContent(bookDTOS);
        bookResponse.setPageNumber(bookPage.getNumber());
        bookResponse.setPageSize(bookPage.getSize());
        bookResponse.setTotalElements(bookPage.getTotalElements());
        bookResponse.setTotalPages(bookPage.getTotalPages());
        bookResponse.setLastPage(bookPage.isLast());
        return bookResponse;
    }

    @Override
    public void issueBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException("Book", "bookId", bookId));

        if(book.getStatus() == BookStatus.ISSUED){
            throw new APIException("Book with book id: "+bookId+" is currently not available.");
        }
        book.setStatus(BookStatus.ISSUED);
        bookRepository.save(book);
    }

    @Override
    public void returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException("Book", "bookId", bookId));

        if(book.getStatus() == BookStatus.AVAILABLE){
            throw new APIException("This book was not issued to anyone.");
        }
        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);
    }
}
