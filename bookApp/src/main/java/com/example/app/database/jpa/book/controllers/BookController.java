package com.example.app.database.jpa.book.controllers;

import com.example.app.database.jpa.book.domain.dto.BookDto;
import com.example.app.database.jpa.book.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class BookController {
    private BookService bookService;


    @PostMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createAuthor(@PathVariable("isbn") String isbn, @RequestBody BookDto book) {
        return new ResponseEntity<>(bookService.save(isbn, book), HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Optional<BookDto> foundBook = bookService.findOne(isbn);
        return foundBook.map(book ->
                new ResponseEntity<>(book, HttpStatus.OK)
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        boolean bookExists = bookService.isExists(isbn);
        BookDto savedUpdatedBookDto = bookService.save(isbn, bookDto);

        if (bookExists) {
            return new ResponseEntity<>(savedUpdatedBookDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(savedUpdatedBookDto, HttpStatus.CREATED);
        }
    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn) {
        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
        if (!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookDto updatedBookDto = bookService.partialUpdate(isbn, bookDto);
        return new ResponseEntity<>(updatedBookDto, HttpStatus.OK);
    }
}
