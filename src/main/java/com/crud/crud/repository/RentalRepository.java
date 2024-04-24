package com.crud.crud.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crud.crud.models.Rental;

public interface RentalRepository extends MongoRepository<Rental, String> {
    List<Rental> findByBookIdAndReturnDateIsNull(String bookId);

    List<Rental> findByRentalDateBeforeAndReturnDateIsNull(String cutoffDate);
}
//