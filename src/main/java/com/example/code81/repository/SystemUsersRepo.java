package com.example.code81.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.code81.entity.SystemUsers;
@Repository
public interface SystemUsersRepo extends JpaRepository<SystemUsers, Long> {

    Optional <SystemUsers> findByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
