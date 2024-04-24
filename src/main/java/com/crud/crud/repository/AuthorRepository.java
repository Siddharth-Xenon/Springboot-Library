package com.crud.crud.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crud.crud.models.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

}
