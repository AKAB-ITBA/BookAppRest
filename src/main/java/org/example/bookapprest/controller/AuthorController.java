package org.example.bookapprest.controller;


import org.example.bookapprest.model.dto.AuthorAddDto;
import org.example.bookapprest.model.dto.AuthorDto;
import org.example.bookapprest.model.dto.BookDto;
import org.example.bookapprest.model.dto.EditAuthorDto;
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
    public AuthorDto getAuthorById(@PathVariable Integer id){
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public BookDto addAuthorToBook(@RequestBody AuthorAddDto authorAddDto){
       return authorService.addAuthorToBook(authorAddDto);
    }

    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable Integer id){
        return authorService.deleteAuthor(id);
    }

    @PatchMapping("/{id}")
    public AuthorDto editAuthorName(@PathVariable Integer id, @RequestBody EditAuthorDto editAuthorDto){
        return authorService.editAuthorName(id, editAuthorDto);
    }

    @PostMapping("/search-author")
    public List<AuthorDto> searchAuthorByAuthorName(@RequestBody AuthorDto authorDto){
        return authorService.searchAuthorByAuthorName(authorDto);
    }
}
