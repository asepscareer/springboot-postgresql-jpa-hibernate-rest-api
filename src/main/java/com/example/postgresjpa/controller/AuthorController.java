package com.example.postgresjpa.controller;

import com.example.postgresjpa.exception.ResourceNotFoundException;
import com.example.postgresjpa.model.Author;
import com.example.postgresjpa.repository.AuthorRepository;
import com.example.postgresjpa.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/authors/{bookId}")
    public List<Author> getAuthorByBookId(@PathVariable Long bookId) {
        return authorRepository.findByBookId(bookId);
    }

    @PostMapping("/authors/{bookId}")
    public Author addAuthor(@PathVariable Long bookId,
                            @Valid @RequestBody Author author) {
        return bookRepository.findById(bookId)
                .map(book -> {
                    author.setBook(book);
                    return authorRepository.save(author);
                }).orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));
    }

    @PutMapping("/authors/{authorId}/{bookId}")
    public Author updateAuthor(@PathVariable Long bookId,
                               @PathVariable Long authorId,
                               @Valid @RequestBody Author authorRequest) {
        if(!bookRepository.existsById(bookId)) {
            throw new ResourceNotFoundException("Book not found with id " + bookId);
        }

        return authorRepository.findById(authorId)
                .map(author -> {
                    author.setName(authorRequest.getName());
                    return authorRepository.save(author);
                }).orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + authorId));
    }

    @DeleteMapping("/authors/{authorId}/{bookId}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long bookId,
                                          @PathVariable Long authorId) {
        if(!bookRepository.existsById(bookId)) {
            throw new ResourceNotFoundException("Book not found with id " + bookId);
        }

        return authorRepository.findById(authorId)
                .map(author -> {
                    authorRepository.delete(author);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + authorId));

    }
}
