package org.example.bookapprest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookapprest.exception.AuthorNotFoundException;
import org.example.bookapprest.mapper.BookMapper;
import org.example.bookapprest.model.dto.AuthorAddDto;
import org.example.bookapprest.model.dto.AuthorDto;
import org.example.bookapprest.model.dto.BookDto;
import org.example.bookapprest.model.dto.EditAuthorDto;
import org.example.bookapprest.model.entity.Author;
import org.example.bookapprest.model.entity.Book;
import org.example.bookapprest.repository.AuthorRepositoryJpa;
import org.example.bookapprest.repository.BookRepositoryJpa;
import org.example.bookapprest.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepositoryJpa authorRepositoryJpa;

    private final BookRepositoryJpa bookRepositoryJpa;

    //private final BookMapper bookMapper;

    @Override
    public List<AuthorDto> getAuthors() {
        List<Author> authors = authorRepositoryJpa.findAll();
        return toAuthorDtoList(authors);
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepositoryJpa.save(author);
    }

    @Override
    public AuthorDto getAuthorById(Integer id) {
        Author author = findAuthorById(id);
        return toAuthorDto(author);
    }

   /* @Override
    public BookDto addAuthorToBook(AuthorAddDto authorAddDto) {
        Book book = bookRepositoryJpa.getBookById(authorAddDto.getBookId());
        Author author = authorRepositoryJpa.findAuthorByAuthorName(authorAddDto.getAuthorName());

        if (author == null) {
            author = new Author();
            author.setAuthorName(authorAddDto.getAuthorName());
            book.getAuthors().add(author);
            author.getBooks().add(book);
        } else {
            book.getAuthors().add(author);
            author.getBooks().add(book);
        }
        authorRepositoryJpa.save(author);
        bookRepositoryJpa.save(book);
        BookServiceImpl bookService = new BookServiceImpl(bookRepositoryJpa, authorRepositoryJpa);
        return bookService.toBookDto(book);


       *//* if(authorRepositoryJpa.save(author) != null
                && bookRepositoryJpa.save(book) != null){
            return "Author adding process completed successfully!";
        }else {
            throw new RuntimeException( "Something goes wrong when adding Author to this book");
        }*//*
    }*/

    @Override
    public BookDto addAuthorToBook(AuthorAddDto authorAddDto) {
        log.debug("addAuthorToBook is started with name: {}, bookId: {}",
                authorAddDto.getAuthorName(), authorAddDto.getBookId());

        Book book = bookRepositoryJpa.getBookById(authorAddDto.getBookId());
        Author author = authorRepositoryJpa.findAuthorByAuthorName(authorAddDto.getAuthorName());
        if (author == null) {
            author = new Author();
            author.setAuthorName(authorAddDto.getAuthorName());
            book.getAuthors().add(author);
            author.getBooks().add(book);
        } else {
            book.getAuthors().add(author);
            author.getBooks().add(book);
        }
        authorRepositoryJpa.save(author);
        bookRepositoryJpa.save(book);
        BookDto bookDto = BookMapper.INSTANCE.toBookDto(book);

        log.trace("addAuthorToBook is ended");
        return bookDto;
    }

    @Override
    public String deleteAuthor(Integer id) {
        /*Optional<Author> authorOptional = authorRepositoryJpa.findById(id);
        Author author = new Author();
        if (authorOptional.isPresent()) {
            author = authorOptional.get();
        }*/
        Author author = findAuthorById(id);
        List<Book> books = author.getBooks();
        String deleteResult = "";
        for (Book book : books) {
            int authorCount = book.getAuthors().size();
            book.getAuthors().remove(author);

            if (authorCount == 1) {
                bookRepositoryJpa.delete(book);
                deleteResult = "Author with one Book was ";
            } else {
                deleteResult = "Author with several Books was ";
            }
        }
        authorRepositoryJpa.delete(author);
        return deleteResult + "deleted";
    }

    @Override
    public AuthorDto editAuthorName(Integer id, EditAuthorDto editAuthorDto) {
        /*Optional<Author> authorOptional = authorRepositoryJpa.findById(id);
        Author author = new Author();
        if (authorOptional.isPresent()) {
            author = authorOptional.get();
        }*/
        Author author = findAuthorById(id);
        author.setAuthorName(editAuthorDto.getAuthorName());
        author = authorRepositoryJpa.save(author);
        return toAuthorDto(author);
    }

    @Override
    public List<AuthorDto> searchAuthorByAuthorName(AuthorDto authorDto) {

        String value = "%".concat(authorDto.getAuthorName()).concat("%");
        List<Author> authors = authorRepositoryJpa.findByAuthorNameLike(value);
        if (authors.isEmpty()) {
            return new ArrayList<>();
        }
        return toAuthorDtoList(authors);
    }

    public Author findAuthorById(Integer id) {
        Optional<Author> authorOptional = authorRepositoryJpa.findById(id);
        if (authorOptional.isPresent()) {
            return authorOptional.get();
        }
        throw new AuthorNotFoundException("author not found with given id: " + id);
        //return authorOptional.orElse(new Author());
    }

    private List<AuthorDto> toAuthorDtoList(List<Author> authors) {
        return authors.stream().map(this::toAuthorDto).toList();
    }

    private AuthorDto toAuthorDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setAuthorName(author.getAuthorName());
        return authorDto;
    }
}
