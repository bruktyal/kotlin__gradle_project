# Library Management System - Implementation Summary

This document summarizes the changes made to fulfill the tasks for the Library Management System project.

## 🛠️ Changes and Implementation Details

### 1. Repository Layer (`InMemoryLibraryRepository.kt`)
- **Implemented all interface methods**:
    - `addBook`: Adds a book to the memory map if the ISBN is unique.
    - `findBook`: Retrieves a book by ISBN.
    - `updateBook`: Updates book details (used for changing availability).
    - `addPatron`: Registers a new patron.
    - `findPatron`: Retrieves a patron by ID.
    - `updatePatron`: Updates patron details (used for borrowing/returning list).
    - `getAllBooks` & `getAllPatrons`: Returns lists of all records.

### 2. Service Layer (`DefaultLibraryService.kt`)
- **Borrowing Logic**:
    - Checks if the book exists and is available.
    - Checks if the patron exists.
    - **Limit Check**: Prevents a patron from borrowing more than 3 books.
    - Updates both the book status and the patron's borrowed list atomically (in memory).
- **Returning Logic**:
    - Reverses the borrowing process.
    - Validates that the patron actually has the book before returning.
- **Search Logic**:
    - Implemented case-insensitive search across both **Title** and **Author** fields.

### 3. UI Layer (`MenuHandler.kt` & `Menu.kt`)
- **Helper Functions**: Created functions to handle console I/O for adding books, registering patrons, borrowing, and returning.
- **Year Validation**: Added a loop to ensure the "Year" input is a valid integer between 0 and 2026.
- **Patron Registration**: Updated to include **Email** and **Phone** fields.
- **Table Display**: Implemented formatted tables for:
    - Search Results
    - Book Listing
    - Patron Listing (includes count of borrowed books)
- **New Feature**: Added a "List All Patrons" option (Option 7) to see registered users and their details.

---

## 🔍 How Search Works (Example)

The search function uses Kotlin's `filter` with `contains(ignoreCase = true)`.

**Example Scenario:**
- **Library has**: 
  1. "Clean Code" by Robert Martin
  2. "Effective Java" by Joshua Bloch
- **User searches for**: "code"
- **Result**: "Clean Code" (Matches Title)
- **User searches for**: "Joshua"
- **Result**: "Effective Java" (Matches Author)

---

## 📈 Summary of Work

| Component | Status Before | Status After |
| :--- | :--- | :--- |
| **InMemoryRepository** | Empty (Only TODOs) | Fully Functional |
| **DefaultLibraryService** | Empty (Only TODOs) | Business logic implemented (Borrow/Return/Search) |
| **MenuHandler** | Switch case only | All actions handle I/O and validation |
| **Library Menu** | Options 0-6 | Options 0-7 (Added List Patrons) |
| **Data Integrity** | None | Added Year validation and Limit checks |
