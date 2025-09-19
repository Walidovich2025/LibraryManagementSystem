# Library Management System - API Testing Examples

This document provides comprehensive examples for testing the Library Management System API using MySQL Workbench or any REST client.

## Base URL
```
http://localhost:8087
```

## Authentication

### 1. Login (Get JWT Token)
**POST** `/api/auth/login`

**Request Body:**
```json
{
    "username": "admin",
    "password": "admin123"
}
```

**Response:**
```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "user": {
        "id": 1,
        "username": "admin",
        "email": "admin@library.com",
        "role": "ADMIN"
    }
}
```

**Headers for subsequent requests:**
```
Authorization: Bearer <your-jwt-token>
```

## Book Management

### 2. Get All Books (Public Access)
**GET** `/api/books`

**Response:**
```json
{
    "content": [
        {
            "id": 1,
            "title": "Harry Potter and the Philosopher's Stone",
            "ISBN": "978-0747532699",
            "language": "English",
            "publication_year": "1997",
            "edition": "1st Edition",
            "summary": "The first book in the Harry Potter series",
            "cover_images": "harry_potter_1.jpg",
            "total_copies": 5,
            "available_copies": 5,
            "authors": [...],
            "publisher": {...},
            "categories": [...]
        }
    ],
    "pageable": {...},
    "totalElements": 3
}
```

### 3. Search Books by Title (Public Access)
**GET** `/api/books/search/title?title=Harry`

### 4. Search Books by Author (Public Access)
**GET** `/api/books/search/author?authorName=Rowling`

### 5. Get Book by ISBN (Public Access)
**GET** `/api/books/isbn/978-0747532699`

### 6. Create Book (Admin/Librarian Only)
**POST** `/api/books`
**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
    "title": "The Great Gatsby",
    "ISBN": "978-0743273565",
    "language": "English",
    "publication_year": "1925",
    "edition": "1st Edition",
    "summary": "A classic American novel",
    "cover_images": "great_gatsby.jpg",
    "total_copies": 3,
    "available_copies": 3
}
```

### 7. Update Book (Admin/Librarian Only)
**PUT** `/api/books/1`
**Headers:** `Authorization: Bearer <token>`

### 8. Delete Book (Admin Only)
**DELETE** `/api/books/1`
**Headers:** `Authorization: Bearer <token>`

## Member Management

### 9. Get All Members (Staff+ Access)
**GET** `/api/members`
**Headers:** `Authorization: Bearer <token>`

### 10. Get Member by ID (Staff+ Access)
**GET** `/api/members/1`
**Headers:** `Authorization: Bearer <token>`

### 11. Create Member (Admin/Librarian Only)
**POST** `/api/members`
**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
    "name": "Alice Johnson",
    "email": "alice.johnson@email.com",
    "phone": "+1-555-0126",
    "membershipStatus": "ACTIVE",
    "maxBorrowLimit": 5
}
```

### 12. Update Member (Admin/Librarian Only)
**PUT** `/api/members/1`
**Headers:** `Authorization: Bearer <token>`

### 13. Delete Member (Admin Only)
**DELETE** `/api/members/1`
**Headers:** `Authorization: Bearer <token>`

## Borrowing Transactions

### 14. Get All Transactions (Staff+ Access)
**GET** `/api/borrowing`
**Headers:** `Authorization: Bearer <token>`

### 15. Borrow Book (Staff+ Access)
**POST** `/api/borrowing/borrow?bookId=1&memberId=1`
**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
{
    "id": 1,
    "borrowDate": "2024-01-15",
    "returnDate": null,
    "status": "BORROWED",
    "member": {...},
    "book": {...},
    "processedBy": {...}
}
```

### 16. Return Book (Staff+ Access)
**POST** `/api/borrowing/return/1`
**Headers:** `Authorization: Bearer <token>`

### 17. Get Transactions by Member (Staff+ Access)
**GET** `/api/borrowing/member/1`
**Headers:** `Authorization: Bearer <token>`

### 18. Get Transactions by Book (Staff+ Access)
**GET** `/api/borrowing/book/1`
**Headers:** `Authorization: Bearer <token>`

### 19. Get Transactions by Status (Staff+ Access)
**GET** `/api/borrowing/status/BORROWED`
**Headers:** `Authorization: Bearer <token>`

## System User Management (Admin Only)

### 20. Get All Users (Admin Only)
**GET** `/api/users`
**Headers:** `Authorization: Bearer <admin-token>`

### 21. Create User (Admin Only)
**POST** `/api/users`
**Headers:** `Authorization: Bearer <admin-token>`

**Request Body:**
```json
{
    "username": "newuser",
    "password": "password123",
    "email": "newuser@library.com",
    "role": {
        "id": 2
    }
}
```

### 22. Update User (Admin Only)
**PUT** `/api/users/1`
**Headers:** `Authorization: Bearer <admin-token>`

### 23. Delete User (Admin Only)
**DELETE** `/api/users/1`
**Headers:** `Authorization: Bearer <admin-token>`

## Author Management

### 24. Get All Authors (Public Access)
**GET** `/api/authors`

### 25. Create Author (Admin/Librarian Only)
**POST** `/api/authors`
**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
    "name": "Mark Twain",
    "biography": "American writer and humorist"
}
```

## Publisher Management

### 26. Get All Publishers (Public Access)
**GET** `/api/publishers`

### 27. Create Publisher (Admin/Librarian Only)
**POST** `/api/publishers`
**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
    "name": "Simon & Schuster",
    "address": "1230 Avenue of the Americas, New York, NY 10020",
    "contactInfo": "+1-212-698-7000"
}
```

## Category Management

### 28. Get All Categories (Public Access)
**GET** `/api/categories`

### 29. Create Category (Admin/Librarian Only)
**POST** `/api/categories`
**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
    "name": "Science Fiction",
    "description": "Science fiction literature"
}
```

## Testing Workflow

### Complete Library Workflow Test:

1. **Login as Admin:**
   ```
   POST /api/auth/login
   Body: {"username": "admin", "password": "admin123"}
   ```

2. **Create a new member:**
   ```
   POST /api/members
   Body: {"name": "Test Member", "email": "test@email.com", "phone": "+1-555-0000"}
   ```

3. **Borrow a book:**
   ```
   POST /api/borrowing/borrow?bookId=1&memberId=1
   ```

4. **Check member's current borrow count:**
   ```
   GET /api/members/1
   ```

5. **Return the book:**
   ```
   POST /api/borrowing/return/1
   ```

6. **Verify book availability:**
   ```
   GET /api/books/1
   ```

## Error Responses

### 401 Unauthorized
```json
{
    "timestamp": "2024-01-15T10:30:00.000+00:00",
    "status": 401,
    "error": "Unauthorized",
    "message": "Unauthorized",
    "path": "/api/books"
}
```

### 403 Forbidden
```json
{
    "timestamp": "2024-01-15T10:30:00.000+00:00",
    "status": 403,
    "error": "Forbidden",
    "message": "Access Denied",
    "path": "/api/users"
}
```

### 400 Bad Request
```json
{
    "timestamp": "2024-01-15T10:30:00.000+00:00",
    "status": 400,
    "error": "Bad Request",
    "message": "Email already exists: test@email.com",
    "path": "/api/members"
}
```

## Initial Data

The system comes pre-loaded with:

### Users:
- **admin** / admin123 (ADMIN role)
- **librarian** / librarian123 (LIBRARIAN role)  
- **staff** / staff123 (STAFF role)

### Books:
- Harry Potter and the Philosopher's Stone (5 copies)
- 1984 (3 copies)
- To Kill a Mockingbird (4 copies)

### Members:
- John Doe (ACTIVE)
- Jane Smith (ACTIVE)
- Bob Johnson (SUSPENDED)

### Authors:
- J.K. Rowling
- George Orwell
- Harper Lee

### Publishers:
- Bloomsbury Publishing
- Penguin Random House
- HarperCollins

### Categories:
- Fiction
- Non-Fiction
- Fantasy
- Dystopian

## Database Setup

1. Create a MySQL database named `library_management`
2. Update `application.properties` with your database credentials
3. Run the application - it will automatically create tables and populate initial data
4. Use the API endpoints above to test the system

## Notes

- All timestamps are in ISO 8601 format
- JWT tokens expire after 5 hours
- Book availability is automatically managed
- Member borrow limits are enforced
- Role-based access control is implemented throughout
- All CRUD operations are available with appropriate permissions
