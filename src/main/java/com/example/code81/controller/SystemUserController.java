package com.example.code81.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.code81.entity.SystemUsers;
import com.example.code81.service.SystemUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class SystemUserController {

    private final SystemUserService systemUserService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SystemUsers>> getAllUsers() {
        List<SystemUsers> users = systemUserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SystemUsers> getUserById(@PathVariable Long id) {
        Optional<SystemUsers> user = systemUserService.getUserById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SystemUsers> getUserByUsername(@PathVariable String username) {
        Optional<SystemUsers> user = systemUserService.getUserByUsername(username);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SystemUsers> createUser(@RequestBody SystemUsers user) {
        try {
            SystemUsers createdUser = systemUserService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SystemUsers> updateUser(@PathVariable Long id, @RequestBody SystemUsers user) {
        try {
            SystemUsers updatedUser = systemUserService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            systemUserService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/check-username/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> checkUsernameExists(@PathVariable String username) {
        boolean exists = systemUserService.usernameExists(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = systemUserService.emailExists(email);
        return ResponseEntity.ok(exists);
    }
}
