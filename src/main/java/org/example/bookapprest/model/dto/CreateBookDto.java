package org.example.bookapprest.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.bookapprest.validator.isbn.Isbn;
import org.example.bookapprest.validator.phonenumber.PhoneNumber;
import org.hibernate.validator.constraints.ISBN;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookDto {

    @NotBlank
    private String title;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String publishedDate;

    @NotBlank
    private String genre;

    //@ISBN(type = ISBN.Type.ISBN_13)
    @Isbn
    private String isbn;

    @PhoneNumber
    private String phoneNumber;

    @NotEmpty
    private List<AuthorDto> authors;

}