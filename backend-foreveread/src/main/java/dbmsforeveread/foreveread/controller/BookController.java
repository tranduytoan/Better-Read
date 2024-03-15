package dbmsforeveread.foreveread.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dbmsforeveread.foreveread.response.BookListResponse;
import dbmsforeveread.foreveread.service.BookRedisService;
import dbmsforeveread.foreveread.service.BookService;
import dbmsforeveread.foreveread.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookRedisService bookRedisService;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

//    @GetMapping("")
//    public ResponseEntity<Page<Book>> getBooks(
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "limit", defaultValue = "5") int limit)
//     {
//            PageRequest pageRequest = PageRequest.of(page, limit,
//                    Sort.by("createAt").descending());
//            Page<Book> books = bookService.getBooks();
//            return new ResponseEntity<>(books, HttpStatus.OK);
//    }
    @GetMapping("")
    public ResponseEntity<BookListResponse> getBooks(
            @RequestParam(defaultValue = "atomic") String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) throws JsonProcessingException {
        int totalPages = 0;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());

        logger.info(String.format("title = %s. page = %d, size = %d", title, page, size));
        List<Book> books = bookRedisService.getAllBooks(title, pageRequest);

        if (books != null && !books.isEmpty()) {
            totalPages = 2;
        }

        if (books == null) {
            Page<Book> bookPage = bookService.getBooks(title, pageRequest);
            totalPages = bookPage.getTotalPages();
            books = bookPage.getContent();
            bookRedisService.saveAllBooks(books, title, pageRequest);
        }

        return ResponseEntity.ok(
                BookListResponse.builder()
                        .books(books)
                        .totalPages(totalPages)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable String id) {
        try {
            Book book = bookRedisService.getBook(id);
            if (book == null) {
                book = bookService.findById(id);
                bookRedisService.saveBook(book);
                logger.info(String.format("id = %s", id));
                System.out.println("Book found with id: " + id);
            }
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Book not found with id: " + id, e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/{author_key}")
//    public ResponseEntity<Book> findByAuthorKeys(@PathVariable String author_key) {
//        try {
//            Book book = bookService.findByAuthorKeys(author_key);
//            return new ResponseEntity<>(book, HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("Author not found with id: " + author_key, e);
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/search")
    public List<Book> search(@RequestParam(value = "searchText") String searchText) {
        return bookService.search(searchText);
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody Book book) {
        Book savedBook;
        try {
            savedBook = bookService.save(book);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Invalid book" + book, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            bookService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Book not found with id: " + id, e);
            return ResponseEntity.notFound().build();
        }
    }
}
