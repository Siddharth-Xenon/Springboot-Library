package com.crud.crud.service;

import com.crud.crud.models.Book;
import com.crud.crud.models.Rental;
import com.crud.crud.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private BookService bookService;

    public Rental rentBook(Rental rental) {
        if (isBookAvailable(rental.getBookId())) {
            rental.setRentalDate(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
            return rentalRepository.save(rental);
        }
        throw new IllegalStateException("Book is currently rented out.");
    }

    public Rental returnBook(String rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalStateException("Rental not found with id: " + rentalId));
        rental.setReturnDate(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
        return rentalRepository.save(rental);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(String id) {
        return rentalRepository.findById(id);
    }

    public boolean isBookAvailable(String bookId) {
        return rentalRepository.findByBookIdAndReturnDateIsNull(bookId).isEmpty();
    }

    public List<Rental> checkOverdueRentals(int days) {
        String cutoffDate = LocalDate.now().minusDays(days).format(DateTimeFormatter.ISO_DATE);
        return rentalRepository.findByRentalDateBeforeAndReturnDateIsNull(cutoffDate);
    }

    public List<Book> getAvailableBooks() {
        List<Rental> allRentals = rentalRepository.findAll();
        List<String> rentedBookIds = allRentals.stream()
                .filter(rental -> rental.getReturnDate() == null)
                .map(Rental::getBookId)
                .collect(Collectors.toList());
        List<Book> allBooks = bookService.getAllBooks();
        return allBooks.stream()
                .filter(book -> !rentedBookIds.contains(book.getId()))
                .collect(Collectors.toList());
    }
}