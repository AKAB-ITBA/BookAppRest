package org.example.bookapprest.service;

import org.example.bookapprest.model.dto.AuthorAddDto;
import org.example.bookapprest.model.dto.AuthorDto;
import org.example.bookapprest.model.dto.BookDto;
import org.example.bookapprest.model.dto.EditAuthorDto;
import org.example.bookapprest.model.entity.Author;
import org.example.bookapprest.model.entity.Book;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();

    Author saveAuthor(Author author);

    AuthorDto getAuthorById(Integer id);

    BookDto addAuthorToBook(AuthorAddDto authorAddDto);

    String deleteAuthor(Integer id);

    AuthorDto editAuthorName(EditAuthorDto editAuthorDto);

    List<AuthorDto> searchAuthorByAuthorName(AuthorDto authorDto);
}
