package com.example.postgresjpa.controller;

import com.example.postgresjpa.exception.ResourceNotFoundException;
import com.example.postgresjpa.model.Book;
import com.example.postgresjpa.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public Page<Book> getbooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }


    @PostMapping("/books")
    public Book addBook(@Valid @RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/books/{bookId}")
    public Book updateBook(@PathVariable Long bookId,
                               @Valid @RequestBody Book bookRequest) {
        return bookRepository.findById(bookId)
                .map(book -> {
                    book.setTitle(bookRequest.getTitle());
                    book.setDescription(bookRequest.getDescription());
                    return bookRepository.save(book);
                }).orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));
    }


    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        return bookRepository.findById(bookId)
                .map(book -> {
                    bookRepository.delete(book);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));
    }
}
