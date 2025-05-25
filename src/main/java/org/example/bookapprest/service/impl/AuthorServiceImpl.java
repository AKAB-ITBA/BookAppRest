package org.example.bookapprest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookapprest.exception.AuthorAlreadyJoinedException;
import org.example.bookapprest.exception.AuthorNotFoundException;
import org.example.bookapprest.exception.AuthorNotSavedException;
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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepositoryJpa authorRepositoryJpa;

    private final BookRepositoryJpa bookRepositoryJpa;

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

    @Override
    public BookDto addAuthorToBook(AuthorAddDto authorAddDto) {
        Book book = bookRepositoryJpa.getBookById(authorAddDto.getBookId());
        Author author = authorRepositoryJpa.findAuthorByAuthorName(authorAddDto.getAuthorName());
        List<Author> authors = book.getAuthors();
        for (Author a : authors) {
            if (a.equals(author)) {
                throw new AuthorAlreadyJoinedException("this author already exist on that book");
            }
        }
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
        Optional<BookDto> bookDtoOpt = Optional.ofNullable(bookDto);
        if (bookDtoOpt.isPresent()) {
            return bookDto;
        } else {
            throw new AuthorNotSavedException("author not found with given parameters ");
        }
    }

    @Override
    public String deleteAuthor(Integer id) {
        Author author = findAuthorById(id);
        List<Book> books = author.getBooks();
        for (Book book : books) {
            int authorCount = book.getAuthors().size();
            book.getAuthors().remove(author);
            if (authorCount == 1) {
                bookRepositoryJpa.delete(book);
            }
        }
        authorRepositoryJpa.delete(author);
        return "deleted";
    }

    @Override
    public AuthorDto editAuthorName(Integer id, EditAuthorDto editAuthorDto) {
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
            throw new AuthorNotFoundException("author not found with given AuthorName: " + authorDto.getAuthorName());
        } else {
            return toAuthorDtoList(authors);
        }
    }

    public Author findAuthorById(Integer id) {
        Optional<Author> authorOptional = authorRepositoryJpa.findById(id);
        if (authorOptional.isPresent()) {
            return authorOptional.get();
        }
        throw new AuthorNotFoundException("author not found with given id: " + id);
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
