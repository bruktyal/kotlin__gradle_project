package org.dbu.library.ui

import org.dbu.library.model.Book
import org.dbu.library.model.Patron
import org.dbu.library.repository.LibraryRepository
import org.dbu.library.service.BorrowResult
import org.dbu.library.service.LibraryService

fun handleMenuAction(
    choice: String,
    service: LibraryService,
    repository: LibraryRepository
): Boolean {

    return when (choice) {

        "1" -> {
            addBook(service)
            true
        }

        "2" -> {
            registerPatron(repository)
            true
        }

        "3" -> {
            borrowBook(service)
            true
        }

        "4" -> {
            returnBook(service)
            true
        }

        "5" -> {
            search(service)
            true
        }

        "6" -> {
            listAllBooks(repository)
            true
        }

        "7" -> {
            listAllPatrons(repository)
            true
        }

        "0" -> false

        else -> {
            println("Invalid option")
            true
        }
    }
}

private fun addBook(service: LibraryService) {
    print("Enter ISBN: ")
    val isbn = readln()
    print("Enter Title: ")
    val title = readln()
    print("Enter Author: ")
    val author = readln()
    print("Enter Year: ")
    var yearStr = readln()
    var year = yearStr.toIntOrNull()
    
    while (year == null || year < 0 || year > 2026) {
        print("Invalid year. Please enter a valid year (0-2026): ")
        yearStr = readln()
        year = yearStr.toIntOrNull()
    }

    val book = Book(isbn, title, author, year)
    if (service.addBook(book)) {
        println("Book added successfully!")
    } else {
        println("Failed to add book (ISBN might already exist).")
    }
}

private fun registerPatron(repository: LibraryRepository) {
    print("Enter Patron ID: ")
    val id = readln()
    print("Enter Name: ")
    val name = readln()
    print("Enter Email: ")
    val email = readln()
    print("Enter Phone: ")
    val phone = readln()

    val patron = Patron(id, name, email, phone)
    if (repository.addPatron(patron)) {
        println("Patron registered successfully!")
    } else {
        println("Failed to register patron (ID might already exist).")
    }
}

private fun borrowBook(service: LibraryService) {
    print("Enter Patron ID: ")
    val patronId = readln()
    print("Enter ISBN: ")
    val isbn = readln()

    val result = service.borrowBook(patronId, isbn)
    when (result) {
        BorrowResult.SUCCESS -> println("Book borrowed successfully!")
        BorrowResult.BOOK_NOT_FOUND -> println("Error: Book not found.")
        BorrowResult.PATRON_NOT_FOUND -> println("Error: Patron not found.")
        BorrowResult.NOT_AVAILABLE -> println("Error: Book is not available.")
        BorrowResult.LIMIT_REACHED -> println("Error: Patron has reached the borrowing limit.")
    }
}

private fun returnBook(service: LibraryService) {
    print("Enter Patron ID: ")
    val patronId = readln()
    print("Enter ISBN: ")
    val isbn = readln()

    if (service.returnBook(patronId, isbn)) {
        println("Book returned successfully!")
    } else {
        println("Error: Could not return book. Check ID and ISBN, or if the book was borrowed.")
    }
}

private fun search(service: LibraryService) {
    print("Enter search query (title or author): ")
    val query = readln()
    val results = service.search(query)
    if (results.isEmpty()) {
        println("No books found.")
    } else {
        println("\nSearch Results:")
        displayBooksTable(results)
    }
}

private fun listAllBooks(repository: LibraryRepository) {
    val books = repository.getAllBooks()
    if (books.isEmpty()) {
        println("No books in library.")
    } else {
        println("\nAll Books List:")
        displayBooksTable(books)
    }
}

private fun listAllPatrons(repository: LibraryRepository) {
    val patrons = repository.getAllPatrons()
    if (patrons.isEmpty()) {
        println("No patrons registered.")
    } else {
        println("\nRegistered Patrons:")
        displayPatronsTable(patrons)
    }
}

private fun displayBooksTable(books: List<Book>) {
    val header = "| %-13s | %-25s | %-20s | %-4s | %-10s |".format("ISBN", "Title", "Author", "Year", "Status")
    val separator = "-".repeat(header.length)
    
    println(separator)
    println(header)
    println(separator)
    
    books.forEach { book ->
        println("| %-13s | %-25s | %-20s | %-4d | %-10s |".format(
            book.isbn.take(13), 
            book.title.take(25), 
            book.author.take(20), 
            book.year, 
            if (book.isAvailable) "Available" else "Borrowed"
        ))
    }
    println(separator)
}

private fun displayPatronsTable(patrons: List<Patron>) {
    val header = "| %-10s | %-15s | %-20s | %-15s | %-8s |".format("ID", "Name", "Email", "Phone", "Borrowed")
    val separator = "-".repeat(header.length)
    
    println(separator)
    println(header)
    println(separator)
    
    patrons.forEach { patron ->
        println("| %-10s | %-15s | %-20s | %-15s | %-8d |".format(
            patron.id.take(10), 
            patron.name.take(15), 
            patron.email.take(20), 
            patron.phone.take(15), 
            patron.borrowedBooks.size
        ))
    }
    println(separator)
}