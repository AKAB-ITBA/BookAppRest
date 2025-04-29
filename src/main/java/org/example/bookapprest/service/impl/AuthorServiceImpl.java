package org.example.bookapprest.service.impl;

import lombok.RequiredArgsConstructor;
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
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepositoryJpa authorRepositoryJpa;

    private final BookRepositoryJpa bookRepositoryJpa;

    @Override
    public List<AuthorDto> getAllAuthors() {
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


       /* if(authorRepositoryJpa.save(author) != null
                && bookRepositoryJpa.save(book) != null){
            return "Author adding process completed successfully!";
        }else {
            throw new RuntimeException( "Something goes wrong when adding Author to this book");
        }*/
    }

    @Override
    public String deleteAuthor(Integer id) {
        Author author = authorRepositoryJpa.findAuthorById(id);
        List<Book> books = author.getBooks();
        String result = "";
        for (Book book : books) {
            int bookCount = book.getAuthors().size();

            if (bookCount == 1) {
                book.getAuthors().remove(author);
                bookRepositoryJpa.delete(book);
                result = "Author with one Book was ";
            } else {
                book.getAuthors().remove(author);
                result = "Author with several Books was ";
            }
        }
        authorRepositoryJpa.delete(author);
        return result + "deleted";
    }

    @Override
    public AuthorDto editAuthorName(EditAuthorDto editAuthorDto) {
        Author author = authorRepositoryJpa.findAuthorById(editAuthorDto.getAuthorId());
        author.setAuthorName(editAuthorDto.getAuthorName());
        Author resultAuthor = authorRepositoryJpa.save(author);
        AuthorDto authorDto = new AuthorDto();
        authorDto.setAuthorName(resultAuthor.getAuthorName());
        return authorDto;
    }

    @Override
    public List<AuthorDto> searchAuthorByAuthorName(AuthorDto authorDto) {
        String value = "%".concat(authorDto.getAuthorName()).concat("%");
        List<Author> authors = authorRepositoryJpa.findByAuthorNameLike(value);
        List<AuthorDto> authorDtoList = authors.stream().map(this::toAuthorDto).toList();
        if (authors.isEmpty()) {
            return null;
        } else {
            return authorDtoList;
        }
    }

    public Author findAuthorById(Integer id) {
        Optional<Author> authorOptional = authorRepositoryJpa.findById(id);
        return authorOptional.orElse(new Author());
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
