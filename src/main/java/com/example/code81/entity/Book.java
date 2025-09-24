package com.example.code81.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
     private String title;
     private String language;
     private String publication_year;

     private String ISBN;
     private String edition;
     private String summary;
     private String  cover_images;

     @ManyToMany
     @JoinTable(name = "book_author",joinColumns = @JoinColumn(name="book_id"),
     inverseJoinColumns = @JoinColumn(name="author_id"))
     private Set<Author> authors=new HashSet<>();

     @ManyToOne
     private Publisher publisher;
     @ManyToMany

     @JoinTable(name = "book_categorie",joinColumns = @JoinColumn(name="book_id"),
     inverseJoinColumns = @JoinColumn(name="categorie_id"))
    private Set<Categorie> categories=new HashSet<>();

    @Column( nullable = false)  
    private Integer available_copies=0;
    @Column(nullable = false)
    private Integer total_copies=0;


}
