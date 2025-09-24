package com.example.code81.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "publishers")
public class BorrowingTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date borrowDate;

    private Date returnDate;

    private String status; // BORROWED, RETURNED, OVERDUE

    @ManyToOne
    private MembersBorrowers member;
    @ManyToOne
    private Book book;

    @ManyToOne
    private SystemUsers processedBy;


    private String remarks;

    private Double fineAmount;




}
