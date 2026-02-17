package com.sb.BookManagement.repositories;

import com.sb.BookManagement.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByBookNameContainingIgnoreCase(String bookName, Pageable pageDetails);

    Page<Book> findByGenreContainingIgnoreCase(String genre, Pageable pageDetails);
}
