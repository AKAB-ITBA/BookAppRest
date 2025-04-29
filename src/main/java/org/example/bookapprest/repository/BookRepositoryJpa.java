package org.example.bookapprest.repository;

import org.example.bookapprest.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepositoryJpa extends JpaRepository<Book, Integer> {

    Book getBookById(int id);

    List<Book> findByTitleLike(String title);

    List<Book> findByGenreLike(String genre);

    List<Book> findByIsbnLike(String isbn);

    @Query(value = "SELECT b.* FROM customers_db.authors2 a " +
            "JOIN customers_db.author_books ab ON a.author_id = ab.author_id " +
            "JOIN customers_db.books2 b ON ab.book_id = b.book_id " +
            "WHERE a.author_name LIKE :authorname", nativeQuery = true)
    List<Book> findByAuthorsLike(String authorname);

}
