package com.example.code81.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.code81.entity.SystemUsers;
import com.example.code81.repository.SystemUsersRepo;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final SystemUsersRepo systemUsersRepo;

    public CustomUserDetailsService(SystemUsersRepo systemUsersRepo) {
        this.systemUsersRepo = systemUsersRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SystemUsers> user = systemUsersRepo.findByUsername(username);
        
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        SystemUsers systemUser = user.get();
        
        return new org.springframework.security.core.userdetails.User(
            systemUser.getUsername(),
            systemUser.getPassword(),
            getAuthorities(systemUser)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(SystemUsers user) {
        String roleName = user.getRole().getRoleName();
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roleName));
    }
}
