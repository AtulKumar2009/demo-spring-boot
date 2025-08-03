package com.example.app.database.jpa.book;

import com.example.app.database.jpa.book.domain.dto.BookDto;
import com.example.app.database.jpa.book.domain.dto.CreateAuthorDto;
import com.example.app.database.jpa.book.domain.dto.ResponseAuthorDto;
import com.example.app.database.jpa.book.domain.entities.AuthorEntity;
import com.example.app.database.jpa.book.domain.entities.BookEntity;

public final class TestDataUtils {
    private TestDataUtils() {
    }

    public static AuthorEntity createTestAuthorEntityA() {
        return AuthorEntity.builder()
//                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static CreateAuthorDto createTestCreateAuthorDtoA() {
        return new CreateAuthorDto("Abigail Rose", 80);
    }

    public static AuthorEntity createTestAuthorEntityB() {
        return AuthorEntity.builder()
//                .id(2L)
                .name("Thomas Cronin")
                .age(44)
                .build();
    }

    public static AuthorEntity createTestAuthorEntityC() {
        return AuthorEntity.builder()
//                .id(3L)
                .name("Jesse A Casey")
                .age(24)
                .build();
    }

    public static BookEntity createTestBookEntityA(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .author(authorEntity)
                .build();
    }

    public static BookDto createTestBookDtoA(final ResponseAuthorDto responseAuthorDto) {
        return BookDto.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .author(responseAuthorDto)
                .build();
    }


    public static BookEntity createTestBookEntityB(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-1-2345-6789-1")
                .title("Beyond the Horizon")
                .author(authorEntity)
                .build();
    }

    public static BookEntity createTestBookEntityC(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-1-2345-6789-2")
                .title("The Last Ember")
                .author(authorEntity)
                .build();
    }
}