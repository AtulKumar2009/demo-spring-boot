package com.example.app.database.jpa.book.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
//        // Map AuthorEntity → ResponseAuthorDto
//        mapper.typeMap(AuthorEntity.class, ResponseAuthorDto.class).addMappings(mp -> {
//            mp.map(AuthorEntity::getName, (dest, v) -> dest.getAuthorDetails().setName((String) v));
//            mp.map(AuthorEntity::getAge, (dest, v) -> dest.getAuthorDetails().setAge((Integer) v));
//        });
//
//        // Map ResponseAuthorDto → AuthorEntity
//        mapper.typeMap(ResponseAuthorDto.class, AuthorEntity.class).addMappings(mp -> {
//            mp.map(src -> src.getAuthorDetails().getName(), AuthorEntity::setName);
//            mp.map(src -> src.getAuthorDetails().getAge(), AuthorEntity::setAge);
//        });

        return mapper;
    }
}
