package com.example.code81.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.code81.entity.*;
import com.example.code81.repository.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;
    
    @Autowired
    private SystemUsersRepo systemUsersRepo;
    
    @Autowired
    private AuthorRepo authorRepo;
    
    @Autowired
    private PublisherRepo publisherRepo;
    
    @Autowired
    private CategorieRepo categorieRepo;
    
    @Autowired
    private BookRepo bookRepo;
    
    @Autowired
    private MembersBorrowersRepo membersBorrowersRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeUsers();
        initializeAuthors();
        initializePublishers();
        initializeCategories();
        initializeBooks();
        initializeMembers();
    }

    private void initializeRoles() {
        if (roleRepo.count() == 0) {
            Role adminRole = Role.builder()
                .roleName("ADMIN")
                .description("Administrator with full access")
                .build();
            roleRepo.save(adminRole);

            Role librarianRole = Role.builder()
                .roleName("LIBRARIAN")
                .description("Librarian with book and member management access")
                .build();
            roleRepo.save(librarianRole);

            Role staffRole = Role.builder()
                .roleName("STAFF")
                .description("Staff with basic access")
                .build();
            roleRepo.save(staffRole);
        }
    }

    private void initializeUsers() {
        if (systemUsersRepo.count() == 0) {
            Role adminRole = roleRepo.findByRoleName("ADMIN").orElseThrow();
            Role librarianRole = roleRepo.findByRoleName("LIBRARIAN").orElseThrow();
            Role staffRole = roleRepo.findByRoleName("STAFF").orElseThrow();

            SystemUsers admin = SystemUsers.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .email("admin@library.com")
                .role(adminRole)
                .creatroleAt(new Date())
                .updateAt(new Date())
                .build();
            systemUsersRepo.save(admin);

            SystemUsers librarian = SystemUsers.builder()
                .username("librarian")
                .password(passwordEncoder.encode("librarian123"))
                .email("librarian@library.com")
                .role(librarianRole)
                .creatroleAt(new Date())
                .updateAt(new Date())
                .build();
            systemUsersRepo.save(librarian);

            SystemUsers staff = SystemUsers.builder()
                .username("staff")
                .password(passwordEncoder.encode("staff123"))
                .email("staff@library.com")
                .role(staffRole)
                .creatroleAt(new Date())
                .updateAt(new Date())
                .build();
            systemUsersRepo.save(staff);
        }
    }

    private void initializeAuthors() {
        if (authorRepo.count() == 0) {
            Author author1 = Author.builder()
                .name("J.K. Rowling")
                .biography("British author, best known for the Harry Potter series")
                .build();
            authorRepo.save(author1);

            Author author2 = Author.builder()
                .name("George Orwell")
                .biography("English novelist, essayist, journalist, and critic")
                .build();
            authorRepo.save(author2);

            Author author3 = Author.builder()
                .name("Harper Lee")
                .biography("American novelist best known for To Kill a Mockingbird")
                .build();
            authorRepo.save(author3);
        }
    }

    private void initializePublishers() {
        if (publisherRepo.count() == 0) {
            Publisher publisher1 = Publisher.builder()
                .name("Bloomsbury Publishing")
                .address("50 Bedford Square, London WC1B 3DP, UK")
                .contactInfo("+44 20 7631 5600")
                .build();
            publisherRepo.save(publisher1);

            Publisher publisher2 = Publisher.builder()
                .name("Penguin Random House")
                .address("80 Strand, London WC2R 0RL, UK")
                .contactInfo("+44 20 7010 3000")
                .build();
            publisherRepo.save(publisher2);

            Publisher publisher3 = Publisher.builder()
                .name("HarperCollins")
                .address("195 Broadway, New York, NY 10007, USA")
                .contactInfo("+1 212-207-7000")
                .build();
            publisherRepo.save(publisher3);
        }
    }

    private void initializeCategories() {
        if (categorieRepo.count() == 0) {
            Categorie fiction = Categorie.builder()
                .name("Fiction")
                .description("Imaginative literature")
                .build();
            categorieRepo.save(fiction);

            Categorie nonFiction = Categorie.builder()
                .name("Non-Fiction")
                .description("Factual literature")
                .build();
            categorieRepo.save(nonFiction);

            Categorie fantasy = Categorie.builder()
                .name("Fantasy")
                .description("Fantasy literature")
                .build();
            categorieRepo.save(fantasy);

            Categorie dystopian = Categorie.builder()
                .name("Dystopian")
                .description("Dystopian literature")
                .build();
            categorieRepo.save(dystopian);
        }
    }

    private void initializeBooks() {
        if (bookRepo.count() == 0) {
            Author rowling = authorRepo.findByName("J.K. Rowling").orElseThrow();
            Author orwell = authorRepo.findByName("George Orwell").orElseThrow();
            Author lee = authorRepo.findByName("Harper Lee").orElseThrow();

            Publisher bloomsbury = publisherRepo.findByName("Bloomsbury Publishing").orElseThrow();
            Publisher penguin = publisherRepo.findByName("Penguin Random House").orElseThrow();
            Publisher harper = publisherRepo.findByName("HarperCollins").orElseThrow();

            Categorie fantasy = categorieRepo.findByName("Fantasy").orElseThrow();
            Categorie dystopian = categorieRepo.findByName("Dystopian").orElseThrow();
            Categorie fiction = categorieRepo.findByName("Fiction").orElseThrow();

            // Harry Potter and the Philosopher's Stone
            Book book1 = Book.builder()
                .title("Harry Potter and the Philosopher's Stone")
                .ISBN("978-0747532699")
                .language("English")
                .publication_year("1997")
                .edition("1st Edition")
                .summary("The first book in the Harry Potter series")
                .cover_images("harry_potter_1.jpg")
                .publisher(bloomsbury)
                .total_copies(5)
                .available_copies(5)
                .build();
            
            Set<Author> authors1 = new HashSet<>();
            authors1.add(rowling);
            book1.setAuthors(authors1);
            
            Set<Categorie> categories1 = new HashSet<>();
            categories1.add(fantasy);
            categories1.add(fiction);
            book1.setCategories(categories1);
            
            bookRepo.save(book1);

            // 1984
            Book book2 = Book.builder()
                .title("1984")
                .ISBN("978-0451524935")
                .language("English")
                .publication_year("1949")
                .edition("1st Edition")
                .summary("A dystopian social science fiction novel")
                .cover_images("1984.jpg")
                .publisher(penguin)
                .total_copies(3)
                .available_copies(3)
                .build();
            
            Set<Author> authors2 = new HashSet<>();
            authors2.add(orwell);
            book2.setAuthors(authors2);
            
            Set<Categorie> categories2 = new HashSet<>();
            categories2.add(dystopian);
            categories2.add(fiction);
            book2.setCategories(categories2);
            
            bookRepo.save(book2);

            // To Kill a Mockingbird
            Book book3 = Book.builder()
                .title("To Kill a Mockingbird")
                .ISBN("978-0061120084")
                .language("English")
                .publication_year("1960")
                .edition("1st Edition")
                .summary("A novel about racial injustice and childhood innocence")
                .cover_images("mockingbird.jpg")
                .publisher(harper)
                .total_copies(4)
                .available_copies(4)
                .build();
            
            Set<Author> authors3 = new HashSet<>();
            authors3.add(lee);
            book3.setAuthors(authors3);
            
            Set<Categorie> categories3 = new HashSet<>();
            categories3.add(fiction);
            book3.setCategories(categories3);
            
            bookRepo.save(book3);
        }
    }

    private void initializeMembers() {
        if (membersBorrowersRepo.count() == 0) {
            MembersBorrowers member1 = MembersBorrowers.builder()
                .name("John Doe")
                .email("john.doe@email.com")
                .phone("+1-555-0123")
                .membershipStatus("ACTIVE")
                .maxBorrowLimit(5)
                .currentBorrowCount(0)
                .creatprocessAt(new Date())
                .updateAt(new Date())
                .build();
            membersBorrowersRepo.save(member1);

            MembersBorrowers member2 = MembersBorrowers.builder()
                .name("Jane Smith")
                .email("jane.smith@email.com")
                .phone("+1-555-0124")
                .membershipStatus("ACTIVE")
                .maxBorrowLimit(5)
                .currentBorrowCount(0)
                .creatprocessAt(new Date())
                .updateAt(new Date())
                .build();
            membersBorrowersRepo.save(member2);

            MembersBorrowers member3 = MembersBorrowers.builder()
                .name("Bob Johnson")
                .email("bob.johnson@email.com")
                .phone("+1-555-0125")
                .membershipStatus("SUSPENDED")
                .maxBorrowLimit(3)
                .currentBorrowCount(0)
                .creatprocessAt(new Date())
                .updateAt(new Date())
                .build();
            membersBorrowersRepo.save(member3);
        }
    }
}
