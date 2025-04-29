package org.example.bookapprest.controller;

import org.example.bookapprest.model.dto.BookDto;
import org.example.bookapprest.model.dto.CreateBookDto;
import org.example.bookapprest.model.dto.EditBookDto;
import org.example.bookapprest.service.BookService;
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
    public BookDto getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Integer id) {
        bookService.deleteBookById(id);
    }

    @PostMapping
    public void createBook(@RequestBody CreateBookDto createBookDto) {
        bookService.createBook(createBookDto);
        //test addAuthorToBook
    }

    @PutMapping("/{id}")
    public void editBook(@PathVariable Integer id, @RequestBody EditBookDto editBookDto) {
        bookService.editBook(id, editBookDto);
    }

    @GetMapping("/searchBook")
    public List<BookDto> searchBook(@RequestParam("value") String value,
                                    @RequestParam("param") String param) {
        return bookService.searchBook(value, param);
    }
}