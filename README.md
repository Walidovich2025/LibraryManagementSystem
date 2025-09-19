# Library Management System

A comprehensive Library Management System built with Java, Spring Boot, and MySQL, featuring role-based access control, JWT authentication, and a complete RESTful API.

## Features

### Core Functionality
- **Book Management**: Complete CRUD operations for books with extended metadata
- **Member Management**: Borrower registration and management
- **Borrowing System**: Book borrowing and return functionality
- **User Management**: System users with role-based access control
- **Search & Filter**: Advanced book search by title, author, ISBN
- **Authentication**: JWT-based secure authentication

### Extended Book Metadata
- Multiple authors per book
- Publisher information
- Category/genre classification
- Language, publication year, edition
- ISBN, summary, cover images
- Copy management (total and available copies)

### Role-Based Access Control
- **Administrator**: Full system access, user management
- **Librarian**: Book and member management, borrowing operations
- **Staff**: Basic operations, borrowing management

### Security Features
- JWT token-based authentication
- Password encryption with BCrypt
- Role-based endpoint protection
- Secure password storage

## Technology Stack

- **Backend**: Java 17, Spring Boot 3.5.6
- **Database**: MySQL 8.0
- **Security**: Spring Security with JWT
- **Build Tool**: Maven
- **ORM**: Spring Data JPA with Hibernate

## Database Schema

### Core Entities
- **Books**: Extended metadata with authors, publishers, categories
- **Members**: Borrower information with membership status
- **SystemUsers**: Staff with role-based access
- **BorrowingTransactions**: Complete borrowing history
- **Authors**: Author information with biography
- **Publishers**: Publisher details with contact information
- **Categories**: Genre classification system
- **Roles**: User role definitions

### Relationships
- Many-to-Many: Books ↔ Authors, Books ↔ Categories
- One-to-Many: Publisher → Books, Member → Transactions
- Many-to-One: Book → Publisher, Transaction → Member/Book/User

## API Design

### RESTful Endpoints
- **Books**: `/api/books` - Full CRUD with search capabilities
- **Members**: `/api/members` - Member management
- **Borrowing**: `/api/borrowing` - Borrow/return operations
- **Users**: `/api/users` - System user management
- **Authors**: `/api/authors` - Author management
- **Publishers**: `/api/publishers` - Publisher management
- **Categories**: `/api/categories` - Category management
- **Authentication**: `/api/auth` - Login/logout

### Security Implementation
- JWT tokens for stateless authentication
- Role-based access control with `@PreAuthorize`
- Password encryption with BCrypt
- Secure session management

## Design Choices

### 1. Entity Design
**Choice**: Extended book metadata with separate entities for authors, publishers, and categories.

**Rationale**: 
- Supports multiple authors per book (many-to-many relationship)
- Enables hierarchical category structure
- Provides comprehensive publisher information
- Allows for complex search and filtering

### 2. Role-Based Access Control
**Choice**: Three-tier role system (Admin, Librarian, Staff) with granular permissions.

**Rationale**:
- Clear separation of responsibilities
- Scalable permission system
- Security through least privilege principle
- Easy to extend with new roles

### 3. Borrowing System
**Choice**: Transaction-based borrowing with automatic copy management.

**Rationale**:
- Maintains data integrity
- Automatic availability tracking
- Complete audit trail
- Prevents over-borrowing

### 4. Authentication & Security
**Choice**: JWT-based stateless authentication with Spring Security.

**Rationale**:
- Scalable for multiple clients
- Stateless server design
- Industry standard security practices
- Easy integration with frontend applications

### 5. Database Design
**Choice**: Normalized schema with proper foreign key relationships.

**Rationale**:
- Data consistency and integrity
- Efficient querying capabilities
- Scalable for large datasets
- Follows database normalization principles

### 6. API Design
**Choice**: RESTful API with proper HTTP methods and status codes.

**Rationale**:
- Industry standard for web APIs
- Easy to consume by frontend applications
- Clear and intuitive endpoint structure
- Proper error handling and status codes

## Initial Data

The system comes pre-configured with:

### Users
- **admin** (password: admin123) - Full system access
- **librarian** (password: librarian123) - Book and member management
- **staff** (password: staff123) - Basic operations

### Sample Data
- 3 books with complete metadata
- 3 library members
- Authors, publishers, and categories
- Role definitions

## Getting Started

### Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd code81
   ```

2. **Configure Database**
   - Create MySQL database: `library_management`
   - Update `application.properties` with your database credentials
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the API**
   - Base URL: `http://localhost:8080`
   - API Documentation: See `API_TESTING_EXAMPLES.md`

### Testing the API

1. **Login to get JWT token**
   ```bash
   curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username":"admin","password":"admin123"}'
   ```

2. **Use token for authenticated requests**
   ```bash
   curl -X GET http://localhost:8080/api/books \
     -H "Authorization: Bearer <your-jwt-token>"
   ```

## API Testing

See `API_TESTING_EXAMPLES.md` for comprehensive testing examples including:
- Authentication flows
- CRUD operations for all entities
- Borrowing workflow
- Error handling scenarios
- Role-based access testing

## Architecture

### Layered Architecture
```
├── Controller Layer (REST APIs)
├── Service Layer (Business Logic)
├── Repository Layer (Data Access)
├── Entity Layer (Domain Models)
└── Security Layer (Authentication & Authorization)
```

### Key Components
- **Controllers**: Handle HTTP requests and responses
- **Services**: Implement business logic and validation
- **Repositories**: Data access abstraction
- **Entities**: Domain models with JPA annotations
- **Security**: JWT authentication and role-based authorization

## Future Enhancements

### Potential Improvements
1. **Frontend Application**: React/Vue.js web interface
2. **Advanced Search**: Full-text search with Elasticsearch
3. **Reporting**: Analytics and reporting dashboard
4. **Notifications**: Email/SMS notifications for due dates
5. **Mobile App**: Mobile application for members
6. **API Documentation**: Swagger/OpenAPI documentation
7. **Testing**: Comprehensive unit and integration tests
8. **Caching**: Redis caching for improved performance
9. **Microservices**: Break down into microservices architecture
10. **Audit Logging**: Comprehensive audit trail

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For questions or support, please contact the development team or create an issue in the repository.
