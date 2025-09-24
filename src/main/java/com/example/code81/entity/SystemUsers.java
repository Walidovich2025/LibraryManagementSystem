package com.example.code81.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "system_users")
public class SystemUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @NotBlank
    private String password;
   
    private Date creatroleAt;
    private Date updateAt;
    
    @Email
    private String email;

    @ManyToOne

    @JoinColumn(name = "role_id", nullable = false)
    private Role role; // e.g., ADMIN, LIBRARIAN





    // Role constants
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_LIBRARIAN = "LIBRARIAN";
    public static final String ROLE_STAFF = "STAFF";

}
