package org.example.bookapprest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookDto {
    private String title;
    private String publishedDate;
    private String genre;
    private String isbn;
    private List<AuthorDto> authors;
}