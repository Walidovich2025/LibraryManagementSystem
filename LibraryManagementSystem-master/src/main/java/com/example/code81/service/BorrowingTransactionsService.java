package com.example.code81.service;

import java.util.List;
import java.util.Optional;
import java.sql.Date;
import java.time.LocalDate;

import com.example.code81.entity.BorrowingTransactions;
import com.example.code81.entity.Book;
import com.example.code81.entity.MembersBorrowers;
import com.example.code81.entity.SystemUsers;

import org.springframework.stereotype.Service;

import com.example.code81.repository.BorrowingTransactionsRepo;
import com.example.code81.repository.BookRepo;
import com.example.code81.repository.MembersBorrowersRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BorrowingTransactionsService {

    private final BorrowingTransactionsRepo borrowingTransactionsRepo;
    private final BookRepo bookRepo;
    private final MembersBorrowersRepo membersBorrowersRepo;


     public List<BorrowingTransactions> getAllTransactions() {
        return borrowingTransactionsRepo.findAll();
    }

    public Optional<BorrowingTransactions> getTransactionById(Long id) {
        return borrowingTransactionsRepo.findById(id);
    }

    public List<BorrowingTransactions> getTransactionsByMember(Long memberId) {
        return borrowingTransactionsRepo.findByMemberId(memberId);
    }

    public List<BorrowingTransactions> getTransactionsByBook(Long bookId) {
        return borrowingTransactionsRepo.findByBookId(bookId);
    }

    public List<BorrowingTransactions> getTransactionsByStatus(String status) {
        return borrowingTransactionsRepo.findByStatus(status);
    }

     public BorrowingTransactions borrowBook(Long bookId, Long memberId, SystemUsers processedBy) {
         // Check if book exists and is available
         Book book = bookRepo.findById(bookId)
             .orElseThrow(() -> new RuntimeException("Book not found"));
         
         if (book.getAvailable_copies() <= 0) {
             throw new IllegalStateException("No available copies of this book");
         }
         
         // Check if member exists and can borrow
         MembersBorrowers member = membersBorrowersRepo.findById(memberId)
             .orElseThrow(() -> new RuntimeException("Member not found"));
         
         if (!"ACTIVE".equals(member.getMembershipStatus())) {
             throw new IllegalStateException("Member is not active");
         }
         
         if (member.getCurrentBorrowCount() >= member.getMaxBorrowLimit()) {
             throw new IllegalStateException("Member has reached borrowing limit");
         }
         
         // Create transaction
         BorrowingTransactions transaction = BorrowingTransactions.builder()
             .book(book)
             .member(member)
             .processedBy(processedBy)
             .borrowDate(Date.valueOf(LocalDate.now()))
             .status("BORROWED")
             .build();
         
         // Update book available copies
         book.setAvailable_copies(book.getAvailable_copies() - 1);
         bookRepo.save(book);
         
         // Update member borrow count
         member.setCurrentBorrowCount(member.getCurrentBorrowCount() + 1);
         membersBorrowersRepo.save(member);
         
         return borrowingTransactionsRepo.save(transaction);
     }

     public BorrowingTransactions returnBook(Long transactionId, SystemUsers processedBy) {
         BorrowingTransactions transaction = borrowingTransactionsRepo.findById(transactionId)
             .orElseThrow(() -> new RuntimeException("Transaction not found"));
         
         if (!"BORROWED".equals(transaction.getStatus())) {
             throw new IllegalStateException("Book is not currently borrowed");
         }
         
         // Update transaction
         transaction.setReturnDate(Date.valueOf(LocalDate.now()));
         transaction.setStatus("RETURNED");
         transaction.setProcessedBy(processedBy);
         
         // Update book available copies
         Book book = transaction.getBook();
         book.setAvailable_copies(book.getAvailable_copies() + 1);
         bookRepo.save(book);
         
         // Update member borrow count
         MembersBorrowers member = transaction.getMember();
         member.setCurrentBorrowCount(member.getCurrentBorrowCount() - 1);
         membersBorrowersRepo.save(member);
         
         return borrowingTransactionsRepo.save(transaction);
     }
        
    

}
