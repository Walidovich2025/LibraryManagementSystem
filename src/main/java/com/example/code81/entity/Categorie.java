package com.example.code81.entity;

import java.util.HashSet;
import java.util.Locale.Category;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name = "categories")
public class Categorie {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
     private String name;
     private String description;

     @ManyToMany(mappedBy = "categories")
     private Set<Book> books=new HashSet<>();


    //   @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private Category parent;
    
    // @OneToMany(mappedBy = "parent")
    // private Set<Category> children = new HashSet<>();

}
