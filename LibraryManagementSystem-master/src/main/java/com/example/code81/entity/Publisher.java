package com.example.code81.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Publisher {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

     private String name;
     private String address;
     private String contactInfo;


     @OneToMany(mappedBy = "publisher")
     private Set<Book> books=new HashSet<>();

}
