package com.example.app.database.jpa.book.services;

import com.example.app.database.jpa.book.domain.dto.CreateAuthorDto;
import com.example.app.database.jpa.book.domain.dto.ResponseAuthorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    ResponseAuthorDto save(CreateAuthorDto authorDto);

    List<ResponseAuthorDto> findAll();

    Page<ResponseAuthorDto> findAll(Pageable pageable);

    Optional<ResponseAuthorDto> findOne(Long id);

    boolean isExists(Long id);

    ResponseAuthorDto partialUpdate(Long id, CreateAuthorDto authorDto);

    void delete(Long id);
}
