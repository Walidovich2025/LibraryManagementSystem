package com.example.code81.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.code81.entity.Author;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);
}
