package dbmsforeveread.foreveread.book;

import dbmsforeveread.foreveread.SearchEngine.BookSearchRequest;
import dbmsforeveread.foreveread.SearchEngine.domain.Product;
import dbmsforeveread.foreveread.SearchEngine.service.ProductService;
import dbmsforeveread.foreveread.category.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final ProductService productService;
//    @Autowired
//    private BookRedisService bookRedisServiceImpli;
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookDetails(@PathVariable Long id) {
//        BookDTO book = bookRedisServiceImpli.getBookFromRedis(id.toString());
//        if (book == null) {
//            book = bookService.getBookDetails(id);
//            bookRedisServiceImpli.addBookToRedis(book);
//        }
//        return ResponseEntity.ok(book);
        return ResponseEntity.ok(bookService.getBookDetails(id));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book createdBook = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/search")
//    public Page<BookSearchResultDTO> searchBooks(BookSearchRequest request) {
////        return bookService.searchBooks(request);
//        return productService.searchProductsByName(request);
//    }

//    @GetMapping("/search")
//    public ResponseEntity<Page<Product>> searchBooks(@RequestParam(required = false) String title,
//                                                     @RequestParam(defaultValue = "0") int page,
//                                                     @RequestParam(defaultValue = "10") int size) {
//        try {
//            Page<Product> products = productService.searchProductsByName(title, PageRequest.of(page, size));
//            return ResponseEntity.ok(products);
//        } catch (Exception e) {
//            log.error("Error occurred while searching books", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @PostMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(@RequestBody BookSearchRequest request,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(defaultValue = "price,asc") String[] sort) {
        log.info("Received BookSearchRequest: {}", request);
        try {
            List<Sort.Order> orders = Arrays.stream(sort)
                    .map(s -> {
                        String[] split = s.split(",");
                        if (split.length == 2) {
                            return new Sort.Order(Sort.Direction.fromString(split[1]), split[0]);
                        } else {
                            return new Sort.Order(Sort.Direction.ASC, "price");
                        }
                    })
                    .collect(Collectors.toList());
            Pageable pageable = PageRequest.of(page, size, Sort.by(orders));

            Page<Product> products = productService.searchProducts(request, pageable);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            log.error("Error when searching books", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{bookId}/categories")
    public ResponseEntity<Set<Category>> getBookCategories(@PathVariable Long bookId) {
        Set<Category> categories = bookService.getBookCategories(bookId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{bookId}/recommendations")
    public ResponseEntity<List<BookRecommendedResponse>> getRecommendedBooks(@PathVariable Long bookId) {
        List<BookRecommendedResponse> recommendedBooks = bookService.getRecommendedBooks(bookId);
        return ResponseEntity.ok(recommendedBooks);
    }
}
