package com.example.code81.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.code81.entity.BorrowingTransactions;
@Repository
public interface BorrowingTransactionsRepo extends JpaRepository<BorrowingTransactions, Long> {

    List<BorrowingTransactions> findByMemberId(Long memberId);
    List<BorrowingTransactions> findByBookId(Long bookId);
    List<BorrowingTransactions> findByStatus(String status);

}
