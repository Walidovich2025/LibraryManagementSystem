package com.example.code81.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.code81.entity.Publisher;
@Repository
public interface PublisherRepo extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByName(String name);
}
