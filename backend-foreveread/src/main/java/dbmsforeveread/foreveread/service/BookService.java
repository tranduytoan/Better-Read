package dbmsforeveread.foreveread.service;

import dbmsforeveread.foreveread.model.Book;
import dbmsforeveread.foreveread.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "books")
public class BookService {

    private final BookRepository bookRepository;
    private final BookRedisService bookRedisService;
    public Page<Book> getBooks(String name, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(name, pageable);
    }
//    public Optional<Book> findById(String id) {
//        return bookRepository.findById(id);
//    }
    public Book findById(String id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void delete(String id) {
        bookRepository.deleteById(id);
    }
}
