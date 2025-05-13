package org.example.bookapprest.controller;

import org.example.bookapprest.model.dto.BookDto;
import org.example.bookapprest.model.dto.CreateBookDto;
import org.example.bookapprest.model.dto.EditBookDto;
import org.example.bookapprest.model.rest.RestResponse;
import org.example.bookapprest.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
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
       /* if (ObjectUtils.isEmpty(bookDto)) {
            return RestResponse.error();
        }*/
        return RestResponse.ok(bookDto);
    }

    @DeleteMapping("/{id}")
    public RestResponse<Void> deleteBookById(@PathVariable Integer id) {
        bookService.deleteBookById(id);
        return RestResponse.ok();
    }

    @PostMapping
    public void createBook(@RequestBody CreateBookDto createBookDto) {
        bookService.createBook(createBookDto);
    }

    @PutMapping("/{id}")
    public void editBook(@PathVariable Integer id, @RequestBody EditBookDto editBookDto) {
        bookService.editBook(id, editBookDto);
    }

    @GetMapping("/search-book")
    public List<BookDto> searchBook(@RequestParam String value,
                                    @RequestParam String param) {
        /*return new ResponseEntity<>(bookService.searchBook(value, param), HttpStatus.CREATED);*/
        return bookService.searchBook(value, param);
    }
}