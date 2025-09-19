package com.example.code81.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.code81.entity.Categorie;

@Repository
public interface CategorieRepo extends JpaRepository<Categorie, Long> {

    Optional<Categorie> findByName(String name);
}
