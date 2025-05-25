package org.example.bookapprest.controller;


import org.example.bookapprest.exception.AuthorAlreadyJoinedException;
import org.example.bookapprest.exception.AuthorCantBeAddedException;
import org.example.bookapprest.exception.LackOfParameterException;
import org.example.bookapprest.model.dto.AuthorAddDto;
import org.example.bookapprest.model.dto.AuthorDto;
import org.example.bookapprest.model.dto.BookDto;
import org.example.bookapprest.model.dto.EditAuthorDto;
import org.example.bookapprest.model.rest.RestResponse;
import org.example.bookapprest.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDto> getAuthors() {
        return authorService.getAuthors();
    }

    @GetMapping("/{id}")
    public RestResponse<AuthorDto> getAuthorById(@PathVariable Integer id) {
        AuthorDto authorDto = authorService.getAuthorById(id);
        return RestResponse.ok(authorDto);
    }

    @PostMapping
    public BookDto addAuthorToBook(@RequestBody AuthorAddDto authorAddDto) {
        try {
            if (authorAddDto.getAuthorName().isEmpty()) {
                throw new AuthorCantBeAddedException("author without author's name cant be added");
            } else if (authorAddDto.getBookId().toString().isEmpty()) {
                throw new AuthorCantBeAddedException("author without book id cant be added");
            }
            return authorService.addAuthorToBook(authorAddDto);
        } catch (AuthorAlreadyJoinedException ex) {
            throw new AuthorAlreadyJoinedException(ex.getMessage());
        } catch (NullPointerException e) {
            throw new LackOfParameterException();
        }

    }

    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable Integer id) {
        try {
            return authorService.deleteAuthor(id);
        } catch (NullPointerException ex) {
            throw new LackOfParameterException();
        }

    }

    @PatchMapping("/{id}")
    public AuthorDto editAuthorName(@PathVariable Integer id, @RequestBody EditAuthorDto editAuthorDto) {
        return authorService.editAuthorName(id, editAuthorDto);
    }

    @PostMapping("/search-author")
    public RestResponse<List<AuthorDto>> searchAuthorByAuthorName(@RequestBody AuthorDto authorDto) {
        List<AuthorDto> authorDtoList = authorService.searchAuthorByAuthorName(authorDto);
        return RestResponse.ok(authorDtoList);
    }
}
