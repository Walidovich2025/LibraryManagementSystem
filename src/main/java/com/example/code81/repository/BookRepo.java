package com.example.code81.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.code81.entity.Book;
@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    Optional<Book> findByISBN(String isbn);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorsNameContainingIgnoreCase(String authorName);
}
