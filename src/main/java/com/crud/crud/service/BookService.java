package com.crud.crud.service;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crud.crud.exceptions.BookNotFoundException;
import com.crud.crud.models.Book;
import com.crud.crud.repository.BookRepository;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book addBook(Book book) {
        book.setId(UUID.randomUUID().toString().replace("-", ""));
        return repository.save(book);
    }

    public ResponseEntity<Book> getBookById(String id) {
        Optional<Book> book = repository.findById(id);
        if (!book.isPresent()) {
            throw new BookNotFoundException("Book not found with id " + id);
        }
        return ResponseEntity.ok(book.get());
    }

    public ResponseEntity<Book> updateBook(String id, Book updatedBook) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + id));

        if (updatedBook.getTitle() != null) {
            book.setTitle(updatedBook.getTitle());
        }
        if (updatedBook.getAuthor() != null) {
            book.setAuthor(updatedBook.getAuthor());
        }
        if (updatedBook.getIsbn() != null) {
            book.setIsbn(updatedBook.getIsbn());
        }
        if (updatedBook.getPublicationYear() != 0) {
            book.setPublicationYear(updatedBook.getPublicationYear());
        }

        final Book updatedBookDetails = repository.save(book);
        return ResponseEntity.ok(updatedBookDetails);
    }

    public List<Book> getBooksByAuthor(String authorName) {
        return repository.findByAuthor(authorName);
    }

    public ResponseEntity<Map<String, String>> deleteBook(String id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + id));
        repository.delete(book);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Book: " + book.getTitle() + " by" + book.getAuthor() + " was deleted successfully.");
        return ResponseEntity.ok(response);
    }
}
