package com.aeguinazu.bookstore.controllers;

import com.aeguinazu.bookstore.models.bookentities.Book;
import com.aeguinazu.bookstore.models.bookentities.BookAuthor;
import com.aeguinazu.bookstore.models.bookentities.BookPublisher;
import com.aeguinazu.bookstore.models.bookentities.Genre;
import com.aeguinazu.bookstore.repositories.BookAuthorRepository;
import com.aeguinazu.bookstore.repositories.BookPublisherRepository;
import com.aeguinazu.bookstore.repositories.BookRepository;
import com.aeguinazu.bookstore.repositories.GenreRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookAuthorRepository bookAuthorRepository;
    @Autowired
    private BookPublisherRepository bookPublisherRepository;
    @Autowired
    private GenreRepository genreRepository;

    @PostMapping(value = "/test")
    public ResponseEntity<Book> testBook(@RequestBody List<JsonNode> body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Book newBook = objectMapper.treeToValue(body.get(0), Book.class);
            BookAuthor author = bookAuthorRepository.findByAuthorName(body.get(0).get("author").asText());
            newBook.setAuthor(author);
            bookRepository.save(newBook);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/book")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @PostMapping(value = "/book")
    public ResponseEntity<Book> createBook(@RequestBody JsonNode book) {
        try {
            Book newBook = new Book(book.get("title").asText(), book.get("description").asText(),
                    book.get("isbn").asText(), book.get("price").asInt(), book.get("format").asText(),
                    book.get("coverImg").asText());
            String author = book.get("author").asText();
            String publisher = book.get("publisher").asText();
            List<String> genres = new ArrayList<>(book.get("genres").size());
            book.get("genres").forEach(jsonNode -> genres.add(jsonNode.asText()));
            if (!bookAuthorRepository.existsByAuthorName(author)) {
                List<Book> authorBooks = new ArrayList<>();
                BookAuthor newAuthor = new BookAuthor(author, authorBooks);
                newBook.setAuthor(bookAuthorRepository.save(newAuthor));
            } else {
                newBook.setAuthor(bookAuthorRepository.findByAuthorName(author));
            }
            if (!bookPublisherRepository.existsByPublisher(publisher)) {
                List<Book> publisherBooks = new ArrayList<>();
                BookPublisher newPublisher = new BookPublisher(publisher, publisherBooks);
                newBook.setPublisher(bookPublisherRepository.save(newPublisher));
            } else {
                newBook.setPublisher(bookPublisherRepository.findByPublisher(publisher));
            }
            for (String genre : genres) {
                if (!genreRepository.existsByGenre(genre)) {
                    Genre newGenre = new Genre(genre);
                    newBook.addGenre(newGenre);
                } else {
                    Genre existingGenre = genreRepository.findByGenre(genre);
                    newBook.addGenre(existingGenre);
                }
            }
            Book _newBook = bookRepository.save(newBook);
            return new ResponseEntity<>(_newBook,HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error del Sistema", e);
        }
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") long id) {
        try {
            Optional<Book> oBook = bookRepository.findById(id);
            if (oBook.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(oBook.get(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error del Sistema", e);
        }
    }

    @GetMapping("/authors")
    public ResponseEntity<List<BookAuthor>> getAllAuthors() {
        try {
            List<BookAuthor> authors = bookAuthorRepository.findAll();
            if (authors.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(authors, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error del Sistema", e);
        }
    }

    @PostMapping("/authors")
    public ResponseEntity<BookAuthor> createAuthor(@RequestBody BookAuthor author) {
        try {
            BookAuthor newAuthor = bookAuthorRepository.save(author);
            return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/publishers")
    public ResponseEntity<List<BookPublisher>> getAllPublishers() {
        try {
            List<BookPublisher> publishers = bookPublisherRepository.findAll();
            if (publishers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(publishers, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error del Sistema", e);
        }
    }

    @PostMapping("/publisher")
    public ResponseEntity<BookPublisher> createPublisher(@RequestBody BookPublisher publisher) {
        try {
            BookPublisher newPublisher = bookPublisherRepository.save(publisher);
            return new ResponseEntity<>(newPublisher, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
