package com.example.app.database.jpa.book.services.impl;

import com.example.app.database.jpa.book.domain.dto.CreateAuthorDto;
import com.example.app.database.jpa.book.domain.dto.ResponseAuthorDto;
import com.example.app.database.jpa.book.domain.entities.AuthorEntity;
import com.example.app.database.jpa.book.mappers.Mapper;
import com.example.app.database.jpa.book.repositories.AuthorRepository;
import com.example.app.database.jpa.book.services.AuthorService;
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
public class AuthorServiceImpl implements AuthorService {
    private Mapper<AuthorEntity, CreateAuthorDto> createAuthorMapper;
    private AuthorRepository authorRepository;
    private Mapper<AuthorEntity, ResponseAuthorDto> responseAuthorMapper;

    @Override
    public ResponseAuthorDto save(CreateAuthorDto authorDto) {
        AuthorEntity authorEntity = createAuthorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);
        return responseAuthorMapper.mapTo(savedAuthorEntity);
    }

    @Override
    public List<ResponseAuthorDto> findAll() {
        Iterable<AuthorEntity> authorEntities = authorRepository.findAll();
        return StreamSupport.stream(authorEntities.spliterator(), false)
                .map(responseAuthorMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ResponseAuthorDto> findAll(Pageable pageable) {
        Page<AuthorEntity> authorEntityPage = authorRepository.findAll(pageable);
        return authorEntityPage.map(authorEntity -> responseAuthorMapper.mapTo(authorEntity));
    }

    @Override
    public Optional<ResponseAuthorDto> findOne(Long id) {
        Optional<AuthorEntity> authorEntity = authorRepository.findById(id);
        return authorEntity.map(responseAuthorMapper::mapTo);
    }

    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public ResponseAuthorDto partialUpdate(Long id, CreateAuthorDto authorDto) {
        AuthorEntity authorEntity = createAuthorMapper.mapFrom(authorDto);
        authorEntity.setId(id);

        return responseAuthorMapper.mapTo(
                authorRepository.findById(id)
                        .map(existingAuthor -> {
                            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
                            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
                            return authorRepository.save(existingAuthor);
                        }).orElseThrow(() -> new RuntimeException("Author does not exist"))
        );
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

}
