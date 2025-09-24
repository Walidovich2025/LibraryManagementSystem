package com.example.code81.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.code81.entity.Book;
import com.example.code81.repository.BookRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepo bookRepo;


    public Book findById(Long id) {
        return bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));


    }

    public List<Book> findAll() {
        return bookRepo.findAll();
    }

    public Book save(Book book) {
        return bookRepo.save(book);
    }

     public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepo.findByISBN(isbn);
    }

     public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepo.findAll(pageable);
    }

      public Page<Book> searchBooks(Specification<Book> spec, Pageable pageable) {
        return bookRepo.findAll(pageable);
    }

    public List<Book> searchBooksByTitle(String title) {
        return bookRepo.findByTitleContainingIgnoreCase(title);
    }


 public List<Book> searchBooksByAuthor(String authorName) {
        return bookRepo.findByAuthorsNameContainingIgnoreCase(authorName);
    }

    public Book createBook(Book book) {
        if (book.getAvailable_copies() == null) {
            book.setAvailable_copies(book.getTotal_copies());
        }
        
        return bookRepo.save(book);
    }



     public Book updateBook(Long id, Book bookinfo) {
        return bookRepo.findById(id).map(book -> {
            book.setTitle(bookinfo.getTitle());
            book.setISBN((bookinfo.getISBN()));
            book.setLanguage((bookinfo.getLanguage()));
            book.setPublication_year((bookinfo.getPublication_year()));
            book.setEdition((bookinfo.getEdition()));
            book.setSummary(bookinfo.getSummary());
            book.setCover_images(bookinfo.getCover_images());
            book.setPublisher(bookinfo.getPublisher());

            
            // Update copies
            if (bookinfo.getTotal_copies() != null) {
                int diff = bookinfo.getTotal_copies() - book.getTotal_copies();
                book.setTotal_copies(bookinfo.getTotal_copies());
                book.setAvailable_copies(book.getAvailable_copies() + diff);
            }
            
            return bookRepo.save(book);
        }).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }
    
    public void deleteBook(Long id) {
        bookRepo.deleteById(id);
    }


     public void incrementAvailableCopies(Long bookId) {
        bookRepo.findById(bookId).ifPresent(book -> {
            book.setAvailable_copies(book.getAvailable_copies() + 1);
            bookRepo.save(book);
        });
    }
    
    public void decrementAvailableCopies(Long bookId) {
        bookRepo.findById(bookId).ifPresent(book -> {
            if (book.getAvailable_copies() > 0) {
                book.setAvailable_copies(book.getAvailable_copies() - 1);
                bookRepo.save(book);
            }
        });



    }


}
