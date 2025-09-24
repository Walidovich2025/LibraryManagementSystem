package com.example.code81.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.code81.entity.SystemUsers;
import com.example.code81.repository.SystemUsersRepo;
import com.example.code81.repository.RoleRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SystemUserService {

    private final SystemUsersRepo systemUsersRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    public List<SystemUsers> getAllUsers() {
        return systemUsersRepo.findAll();
    }

    public Optional<SystemUsers> getUserById(Long id) {
        return systemUsersRepo.findById(id);
    }

    public Optional<SystemUsers> getUserByUsername(String username) {
        return systemUsersRepo.findByUsername(username);
    }

    public SystemUsers createUser(SystemUsers user) {
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Set default role if not specified
        if (user.getRole() == null) {
            roleRepo.findByRoleName("STAFF")
                .ifPresent(user::setRole);
        }
        
        return systemUsersRepo.save(user);
    }


    public SystemUsers updateUser(Long id, SystemUsers userinfo) {
        return systemUsersRepo.findById(id).map(user -> {

            user.setUsername(userinfo.getUsername());
            user.setCreatroleAt(userinfo.getCreatroleAt());
            user.setUpdateAt(userinfo.getUpdateAt());
            // user.setEmail(userinfo.getEmail());
            
            
            if (userinfo.getPassword() != null && !userinfo.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userinfo.getPassword()));
            }
            
            if (userinfo.getRole() != null) {
                user.setRole(userinfo.getRole());
            }
            
            return systemUsersRepo.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    public void deleteUser(Long id) {
        systemUsersRepo.deleteById(id);
    }
    public boolean usernameExists(String username) {
        return systemUsersRepo.existsByUsername(username);
    }
    
    public boolean emailExists(String email) {
        return systemUsersRepo.existsByEmail(email);
    }
    

}
