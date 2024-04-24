package com.crud.crud.controller;

import com.crud.crud.models.Book;
import com.crud.crud.models.Rental;
import com.crud.crud.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping
    public ResponseEntity<Rental> createRental(@RequestBody Rental rental) {
        return ResponseEntity.ok(rentalService.rentBook(rental));
    }

    @GetMapping("/{rentalId}")
    public ResponseEntity<Rental> getRentalById(@PathVariable String rentalId) {
        return rentalService.getRentalById(rentalId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/available-books")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        return ResponseEntity.ok(rentalService.getAvailableBooks());
    }

    @GetMapping("/rented-books")
    public ResponseEntity<List<Rental>> getRentedBooks() {
        return ResponseEntity.ok(rentalService.getAllRentals().stream()
                .filter(rental -> rental.getReturnDate() == null)
                .toList());
    }
}

/**
 * Sample JSON request for creating a rental:
 * 
 * {
 * "bookId": "12345",
 * "renterName": "John Doe"
 * }
 *
 * This JSON object represents the minimum required fields to create a new
 * rental.
 * - `bookId` is the identifier of the book being rented.
 * - `renterName` is the name of the person who is renting the book.
 */
