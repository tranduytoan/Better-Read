package dbmsforeveread.foreveread.book.controller;

import dbmsforeveread.foreveread.book.BookService;
import dbmsforeveread.foreveread.book.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable String id) {
        try {
            Book book = bookService.findById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Book not found with id: " + id, e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public List<Book> search(@RequestParam(value = "searchText") String searchText) {
        return bookService.search(searchText);
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody Book book) {
        Book savedBook;
        try {
            savedBook = bookService.save(book);
        } catch (Exception e) {
            logger.error("Invalid book" + book, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            bookService.delete(id);
        } catch (Exception e) {
            logger.error("Book not found with id: " + id, e);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
