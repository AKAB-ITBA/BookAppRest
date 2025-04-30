package org.example.bookapprest.repository;

import org.example.bookapprest.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepositoryJpa extends JpaRepository<Author, Integer> {

    List<Author> findAllByOrderById();

    Author findAuthorByAuthorName(String authorName);

    //Author findAuthorById(int id);

    List<Author> findByAuthorNameLike(String authorName);
}
