package com.example.app.database.jpa.book.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class CreateAuthorDto {

    private String name;
    private Integer age;
}
