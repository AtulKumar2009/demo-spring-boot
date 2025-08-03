package com.example.app.database.jpa.book.mappers.impl;

import com.example.app.database.jpa.book.domain.dto.BookDto;
import com.example.app.database.jpa.book.domain.entities.BookEntity;
import com.example.app.database.jpa.book.mappers.Mapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {
    private ModelMapper modelMapper;

    @Override
    public BookDto mapTo(BookEntity BookEntity) {
        return modelMapper.map(BookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto BookDto) {
        return modelMapper.map(BookDto, BookEntity.class);
    }
}
