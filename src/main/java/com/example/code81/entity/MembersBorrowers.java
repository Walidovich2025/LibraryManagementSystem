package com.example.code81.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
@Table(name = "members_borrowers")
public class MembersBorrowers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column( unique = true, nullable = false)
    @Email
    private String email;
    private String phone;
    private Date creatprocessAt;
    private Date updateAt;

    @Column( nullable = false)
    private String membershipStatus = "ACTIVE"; // ACTIVE, SUSPENDED, EXPIRED
    
    @Column(nullable = false)
    private Integer maxBorrowLimit = 5;
    
    @Column(nullable = false)
    private Integer currentBorrowCount = 0;
}
