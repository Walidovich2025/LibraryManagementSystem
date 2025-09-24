package com.example.code81.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.example.code81.entity.SystemUsers;
import com.example.code81.security.JwtTokenUtil;
import com.example.code81.service.SystemUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final SystemUserService systemUserService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        try {
            log.info("Login attempt for user: {}", loginRequest.getUsername());
            
            // Validate input
            if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(createErrorResponse("Username is required"));
            }
            
            if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(createErrorResponse("Password is required"));
            }

            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername().trim(), 
                    loginRequest.getPassword().trim()
                )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtTokenUtil.generateToken(userDetails);

            // Get user details
            SystemUsers user = systemUserService.getUserByUsername(loginRequest.getUsername().trim())
                .orElseThrow(() -> new UsernameNotFoundException("User not found after authentication"));

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("token_type", "Bearer");
            response.put("expires_in", 86400); // 24 hours
            response.put("user", Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "role", user.getRole().getRoleName()
            ));
            response.put("timestamp", System.currentTimeMillis());
            response.put("status", "success");
            response.put("message", "Login successful");

            log.info("Login successful for user: {}", loginRequest.getUsername());
            return ResponseEntity.ok(response);
            
        } catch (BadCredentialsException e) {
            log.warn("Login failed - invalid credentials for user: {}", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Invalid username or password"));
                    
        } catch (UsernameNotFoundException e) {
            log.warn("Login failed - user not found: {}", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("User not found"));
                    
        } catch (Exception e) {
            log.error("Login error for user {}: {}", loginRequest.getUsername(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Authentication failed: " + e.getMessage()));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(createErrorResponse("Invalid authorization header"));
            }

            String token = authHeader.substring(7);
            // Here you would validate the token and return appropriate response
            Map<String, Object> response = new HashMap<>();
            response.put("status", "valid");
            response.put("message", "Token is valid");
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Invalid token"));
        }
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        errorResponse.put("timestamp", System.currentTimeMillis());
        errorResponse.put("status", "error");
        return errorResponse;
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}