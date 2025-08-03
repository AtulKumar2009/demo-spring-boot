package com.example.app.database.jpa.book.services;

import com.example.app.database.jpa.book.domain.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookDto save(String isbn, BookDto bookDto);

    List<BookDto> findAll();

    Page<BookDto> findAll(Pageable pageable);

    Optional<BookDto> findOne(String isbn);

    boolean isExists(String isbn);

    BookDto partialUpdate(String isbn, BookDto bookDto);

    void delete(String isbn);
}
