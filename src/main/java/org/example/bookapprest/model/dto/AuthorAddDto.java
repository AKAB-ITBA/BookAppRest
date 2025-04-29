package org.example.bookapprest.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorAddDto {
    private String authorName;
    private Integer bookId;
}
