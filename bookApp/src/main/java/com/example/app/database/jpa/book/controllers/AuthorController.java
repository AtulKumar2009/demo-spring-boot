package com.example.app.database.jpa.book.controllers;

import com.example.app.database.jpa.book.domain.dto.CreateAuthorDto;
import com.example.app.database.jpa.book.domain.dto.ResponseAuthorDto;
import com.example.app.database.jpa.book.services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
public class AuthorController {
    private AuthorService authorService;


    @PostMapping(path = "/authors")
    public ResponseEntity<ResponseAuthorDto> createAuthor(@RequestBody CreateAuthorDto author) {
        return new ResponseEntity<>(authorService.save(author), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    public ResponseEntity<Page<ResponseAuthorDto>> getAllAuthors(Pageable pageable) {
        Page<ResponseAuthorDto> savedAuthorEntity = authorService.findAll(pageable);
        return new ResponseEntity<>(savedAuthorEntity, HttpStatus.OK);
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<ResponseAuthorDto> getAuthor(@PathVariable("id") Long id) {
        Optional<ResponseAuthorDto> foundAuthor = authorService.findOne(id);
        return foundAuthor.map(authorDto ->
                new ResponseEntity<>(authorDto, HttpStatus.OK)
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<ResponseAuthorDto> fullUpdateAuthor(
            @PathVariable("id") Long id,
            @RequestBody ResponseAuthorDto authorDto) {
        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorDto.setId(id);
        ResponseAuthorDto savedAuthorDto = authorService.save(authorDto);
        return new ResponseEntity<>(savedAuthorDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity deleteAuthor(@PathVariable("id") Long id) {
        authorService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<ResponseAuthorDto> partialUpdate(
            @PathVariable("id") Long id,
            @RequestBody CreateAuthorDto authorDto
    ) {
        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ResponseAuthorDto updatedAuthor = authorService.partialUpdate(id, authorDto);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }
}
