package com.crud.crud.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Book getBookById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id " + id));
    }
}
