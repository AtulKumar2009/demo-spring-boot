package com.example.app.database.jpa.book.mappers.impl;

import com.example.app.database.jpa.book.domain.dto.CreateAuthorDto;
import com.example.app.database.jpa.book.domain.entities.AuthorEntity;
import com.example.app.database.jpa.book.mappers.Mapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CreateAuthorMapperImpl implements Mapper<AuthorEntity, CreateAuthorDto> {
    private ModelMapper modelMapper;

    @Override
    public CreateAuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, CreateAuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(CreateAuthorDto createAuthorDto) {
        return modelMapper.map(createAuthorDto, AuthorEntity.class);
    }
}
