package org.example.bookapprest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditAuthorDto {
    private Integer authorId;
    private String authorName;
}
