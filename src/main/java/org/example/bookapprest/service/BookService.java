package org.example.bookapprest.service;

import org.example.bookapprest.model.dto.BookDto;
import org.example.bookapprest.model.dto.CreateBookDto;
import org.example.bookapprest.model.dto.EditBookDto;
import org.example.bookapprest.model.entity.Book;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto getBookById(Integer id);

    void deleteBookById(Integer id);

    void createBook(CreateBookDto createBookDto);

    void editBook(Integer id, EditBookDto editBookDto);

    List<BookDto> searchBook(String value, String param);
}
