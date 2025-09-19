package com.example.code81.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.code81.entity.BorrowingTransactions;
import com.example.code81.entity.SystemUsers;
import com.example.code81.service.BorrowingTransactionsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/borrowing")
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingTransactionsService borrowingTransactionsService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<List<BorrowingTransactions>> getAllTransactions() {
        List<BorrowingTransactions> transactions = borrowingTransactionsService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<BorrowingTransactions> getTransactionById(@PathVariable Long id) {
        Optional<BorrowingTransactions> transaction = borrowingTransactionsService.getTransactionById(id);
        return transaction.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<List<BorrowingTransactions>> getTransactionsByMember(@PathVariable Long memberId) {
        List<BorrowingTransactions> transactions = borrowingTransactionsService.getTransactionsByMember(memberId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/book/{bookId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<List<BorrowingTransactions>> getTransactionsByBook(@PathVariable Long bookId) {
        List<BorrowingTransactions> transactions = borrowingTransactionsService.getTransactionsByBook(bookId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<List<BorrowingTransactions>> getTransactionsByStatus(@PathVariable String status) {
        List<BorrowingTransactions> transactions = borrowingTransactionsService.getTransactionsByStatus(status);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/borrow")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<BorrowingTransactions> borrowBook(
            @RequestParam Long bookId,
            @RequestParam Long memberId,
            Authentication authentication) {
        try {
            // Get current user from authentication
            SystemUsers currentUser = (SystemUsers) authentication.getPrincipal();
            
            BorrowingTransactions transaction = borrowingTransactionsService.borrowBook(bookId, memberId, currentUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/return/{transactionId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<BorrowingTransactions> returnBook(
            @PathVariable Long transactionId,
            Authentication authentication) {
        try {
            // Get current user from authentication
            SystemUsers currentUser = (SystemUsers) authentication.getPrincipal();
            
            BorrowingTransactions transaction = borrowingTransactionsService.returnBook(transactionId, currentUser);
            return ResponseEntity.ok(transaction);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
