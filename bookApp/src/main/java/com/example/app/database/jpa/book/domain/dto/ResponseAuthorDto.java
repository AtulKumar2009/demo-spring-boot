package com.example.app.database.jpa.book.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseAuthorDto extends CreateAuthorDto {
    private Long id;
}
