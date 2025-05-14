package org.example.bookapprest.controller;

import org.example.bookapprest.exception.BookAlreadyExistException;
import org.example.bookapprest.exception.BookCantBeSavedException;
import org.example.bookapprest.exception.LackOfParameterException;
import org.example.bookapprest.model.dto.BookDto;
import org.example.bookapprest.model.dto.CreateBookDto;
import org.example.bookapprest.model.dto.EditBookDto;
import org.example.bookapprest.model.rest.RestResponse;
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
    public void createBook(@RequestBody CreateBookDto createBookDto) {
        try {
            if (createBookDto.getAuthors().isEmpty()) {
                throw new BookCantBeSavedException("book without author cant be created");
            } else if (createBookDto.getIsbn().isEmpty()) {
                throw new BookCantBeSavedException("book without isbn code cant be created");
            } else if (createBookDto.getGenre().isEmpty()) {
                throw new BookCantBeSavedException("book without genre code cant be created");
            } else if (createBookDto.getTitle().isEmpty()) {
                throw new BookCantBeSavedException("book without title code cant be created");
            } else if (createBookDto.getPublishedDate().isEmpty()) {
                throw new BookCantBeSavedException("book without published date code cant be created");
            }
            bookService.createBook(createBookDto);
        } catch (BookAlreadyExistException ex) {
            throw new BookAlreadyExistException("book with this " + ex.getMessage() + " already exist");
        } catch (NullPointerException e) {
            throw new LackOfParameterException();
        }
    }

    @PutMapping("/{id}")
    public void editBook(@PathVariable Integer id, @RequestBody EditBookDto editBookDto) {
        bookService.editBook(id, editBookDto);
    }

    @GetMapping("/search-book")
    public List<BookDto> searchBook(@RequestParam String value,
                                    @RequestParam String param) {
        return bookService.searchBook(value, param);
    }
}