package org.example.bookapprest.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.bookapprest.model.dto.BookDto;
import org.example.bookapprest.model.dto.CreateBookDto;
import org.example.bookapprest.model.dto.EditBookDto;
import org.example.bookapprest.model.rest.RestResponse;
import org.example.bookapprest.service.BookService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDto> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public RestResponse<BookDto> getBookById(@PathVariable Integer id) {
        BookDto bookDto = bookService.getBookById(id);
        return RestResponse.ok(bookDto);
    }

    @DeleteMapping("/{id}")
    public RestResponse<Void> deleteBookById(@PathVariable Integer id) {
        bookService.deleteBookById(id);
        return RestResponse.ok();
    }

    @PostMapping
    public String createBook(@RequestBody @Valid CreateBookDto createBookDto) {
        bookService.createBook(createBookDto);
        return createBookDto.getPhoneNumber();
    }

    @PutMapping("/{id}")
    public void editBook(@PathVariable Integer id, @RequestBody EditBookDto editBookDto) {
        bookService.editBook(id, editBookDto);
    }

    @GetMapping("/search-book")
    public List<BookDto> searchBook(@RequestParam @NotBlank String value,
                                    @RequestParam @Size(min = 2, max = 6) String param) {
        return bookService.searchBook(value, param);
    }
}