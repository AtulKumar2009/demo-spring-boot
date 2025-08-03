package com.example.app.database.jpa.book.mappers.impl;

import com.example.app.database.jpa.book.domain.dto.ResponseAuthorDto;
import com.example.app.database.jpa.book.domain.entities.AuthorEntity;
import com.example.app.database.jpa.book.mappers.Mapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ResponseAuthorMapperImpl implements Mapper<AuthorEntity, ResponseAuthorDto> {
    private ModelMapper modelMapper;

    @Override
    public ResponseAuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, ResponseAuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(ResponseAuthorDto responseAuthorDto) {
        return modelMapper.map(responseAuthorDto, AuthorEntity.class);
    }
}
