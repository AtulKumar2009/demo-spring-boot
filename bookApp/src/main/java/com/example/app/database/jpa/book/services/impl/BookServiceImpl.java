package com.example.app.database.jpa.book.services.impl;

import com.example.app.database.jpa.book.domain.dto.BookDto;
import com.example.app.database.jpa.book.domain.entities.BookEntity;
import com.example.app.database.jpa.book.mappers.Mapper;
import com.example.app.database.jpa.book.repositories.BookRepository;
import com.example.app.database.jpa.book.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private Mapper<BookEntity, BookDto> bookMapper;

    @Override
    public BookDto save(String isbn, BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        bookEntity.setIsbn(isbn);
        BookEntity saveBookEntity = bookRepository.save(bookEntity);
        return bookMapper.mapTo(saveBookEntity);
    }

    @Override
    public List<BookDto> findAll() {
        Iterable<BookEntity> bookEntities = bookRepository.findAll();
        return StreamSupport.stream(bookEntities.spliterator(), false)
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        Page<BookEntity> bookEntityPage = bookRepository.findAll(pageable);
        return bookEntityPage.map(bookEntity -> bookMapper.mapTo(bookEntity));
    }

    @Override
    public Optional<BookDto> findOne(String isbn) {
        return bookRepository.findById(isbn).map(bookMapper::mapTo);
    }

    @Override
    public boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public BookDto partialUpdate(String isbn, BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        bookEntity.setIsbn(isbn);
        return bookMapper.mapTo(
                bookRepository.findById(isbn)
                        .map(existingBook -> {
                            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
                            return bookRepository.save(existingBook);
                        }).orElseThrow(() -> new RuntimeException("Book does not exist"))
        );
    }

    @Override
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
