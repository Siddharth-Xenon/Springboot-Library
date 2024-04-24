package com.crud.crud.repository;

import com.crud.crud.models.Book;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByAuthor(String authorName);

}
