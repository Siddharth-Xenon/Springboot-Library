package com.crud.crud.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crud.crud.models.Author;
import com.crud.crud.repository.AuthorRepository;

@Service
public class AuthorService {
    @Autowired

    private AuthorRepository repository;

    public Author addAuthor(Author author) {
        author.setId(UUID.randomUUID().toString().replace("-", ""));
        return repository.save(author);
    }

    public List<Author> getAllAuthors() {
        return repository.findAll();
    }

    public ResponseEntity<Author> getAuthorById(String id) {
        Author author = repository.findById(id).orElse(null);
        if (author != null) {
            return ResponseEntity.ok(author);
        }
        return ResponseEntity.notFound().build();
    }

    public Author updateAuthor(String id, Author updatedAuthor) {
        if (repository.existsById(id)) {
            updatedAuthor.setId(id);
            return repository.save(updatedAuthor);
        }
        return null;
    }

    public ResponseEntity<Map<String, String>> deleteAuthor(String id) {
        Author author = repository.findById(id).orElse(null);
        if (author != null) {
            repository.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "Author deleted successfully"));
        }
        return ResponseEntity.notFound().build();
    }
}
