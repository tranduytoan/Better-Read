package dbmsforeveread.foreveread.controller;

import dbmsforeveread.foreveread.model.Book;
import dbmsforeveread.foreveread.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public Book findById(String id) {
        return bookService.findById(id);
    }

    @GetMapping("/search")
    public List<Book> search(@RequestParam(value = "searchText") String searchText) {
        return bookService.search(searchText);
    }

    @PostMapping
    public Book save(Book book) {
        return bookService.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(String id) {
        bookService.delete(id);
    }

}
