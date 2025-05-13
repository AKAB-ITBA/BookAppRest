package org.example.bookapprest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookapprest.exception.BookNotFoundException;
import org.example.bookapprest.mapper.BookMapper;
import org.example.bookapprest.model.dto.AuthorDto;
import org.example.bookapprest.model.dto.BookDto;
import org.example.bookapprest.model.dto.CreateBookDto;
import org.example.bookapprest.model.dto.EditBookDto;
import org.example.bookapprest.model.entity.Author;
import org.example.bookapprest.model.entity.Book;
import org.example.bookapprest.repository.AuthorRepositoryJpa;
import org.example.bookapprest.repository.BookRepositoryJpa;
import org.example.bookapprest.service.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepositoryJpa bookRepositoryJpa;

    private final AuthorRepositoryJpa authorRepositoryJpa;

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepositoryJpa.findAll();
        return toBookDtoList(books);
    }

    @Override
    public BookDto getBookById(Integer id) {
        /*try {*/
        Book book = findBookById(id);
        return BookMapper.INSTANCE.toBookDto(book);
       /* } catch (BookNotFoundException exception) {
            log.error("getBookById.exception: {}", exception.getMessage());
        } catch (Exception exception) {
            log.error("getBookById.exception: {}", exception.getMessage());
        }
        return null;*/
    }

    @Override
    public void deleteBookById(Integer id) {
        bookRepositoryJpa.deleteById(id);
    }

    @Override
    public void createBook(CreateBookDto createBookDto) {
        bookRepositoryJpa.save(toBook(createBookDto));
    }

    @Override
    public void editBook(Integer id, EditBookDto editBookDto) {
        Book book = findBookById(id);
        if (book.getIsbn() != null) {
            book.setGenre(editBookDto.getGenre());
            book.setTitle(editBookDto.getTitle());
        }
        bookRepositoryJpa.save(book);
    }

    @Override
    public List<BookDto> searchBook(String value, String param) {
        List<Book> books = new ArrayList<>();
        String resultValue = "%".concat(value).concat("%");
        if ("title".equals(param)) {
            books = bookRepositoryJpa.findByTitleLike(resultValue);
        } else if (param.equals("genre")) {
            books = bookRepositoryJpa.findByGenreLike(resultValue);
        } else if (param.equals("isbn")) {
            books = bookRepositoryJpa.findByIsbnLike(resultValue);
        } else if (param.equals("author")) {
            books = bookRepositoryJpa.findByAuthorsLike(resultValue);
        }

        List<BookDto> bookDtoList = toBookDtoList(books);
        if (books.isEmpty()) {
            return null;
        } else {
            return bookDtoList;
        } //criteria api
    }

    private List<BookDto> toBookDtoList(List<Book> books) {
        return books.stream().map(BookMapper.INSTANCE::toBookDto).toList();
    }

   /* private BookDto toBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getTitle());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setGenre(book.getGenre());
        bookDto.setCreatedAt(ObjectUtils.nullSafeToString(book.getCreatedAt()));
        bookDto.setUpdatedAt(book.getUpdatedAt().toString());
        bookDto.setPublishedDate(book.getPublishedDate());
        bookDto.setAuthors(toAuthorDtoList(book.getAuthors()));
        return bookDto;
    }*/

    private List<AuthorDto> toAuthorDtoList(List<Author> authors) {
        return authors.stream().map(this::toAuthorDto).toList();
    }

    private List<Author> toAuthorList(List<AuthorDto> authorDtoList) {
        return authorDtoList.stream().map(this::toAuthor).toList();
    }

    private Author toAuthor(AuthorDto authorDto) {
        return authorRepositoryJpa.findAuthorByAuthorName(authorDto.getAuthorName());
    }

    private AuthorDto toAuthorDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setAuthorName(author.getAuthorName());
        return authorDto;
    }

    private Book toBook(CreateBookDto createBookDto) {
        Book book = new Book();
        List<Author> authorList = toAuthorList(createBookDto.getAuthors());
        book.setTitle(createBookDto.getTitle());
        book.setIsbn(createBookDto.getIsbn());
        book.setGenre(createBookDto.getGenre());
        book.setPublishedDate(createBookDto.getPublishedDate());
        book.setAuthors(authorList);
        return book;
    }

    private Book findBookById(Integer id) {
        Optional<Book> bookOptional = bookRepositoryJpa.findById(id);
        if (bookOptional.isPresent()) {
            return bookOptional.get();
        }
        throw new BookNotFoundException("book not found with given id: " + id, BookServiceImpl.class.getName());
        //return bookOptional.orElse(new Book());
    }
}