package org.example.bookapprest.repository;

import org.example.bookapprest.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepositoryJpa extends JpaRepository<Author, Integer> {

    Author findAuthorByAuthorName(String authorName);

    List<Author> findByAuthorNameLike(String authorName);
}
