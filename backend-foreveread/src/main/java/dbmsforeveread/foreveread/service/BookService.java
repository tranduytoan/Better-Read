package dbmsforeveread.foreveread.service;

import dbmsforeveread.foreveread.model.Book;
import dbmsforeveread.foreveread.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
//    public Page<Book> getBooks() {
//        return bookRepository.findAll(PageRequest.of(0, 5));
//    }

    public Page<Book> getBooks(String name, PageRequest pageRequest) {
        return bookRepository.findByTitleContainingIgnoreCase(name, pageRequest);
    }
    public Book findById(String id) {
        return bookRepository.findById(id).orElse(null);
    }
//    public Book findByAuthorKeys(String author_key) {
//        return (Book) bookRepository.findByAuthorKeys(author_key).orElse(null);
//    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void delete(String id) {
        bookRepository.deleteById(id);
    }
    public List<Book> search(String searchText) {
        return bookRepository.findByTitleOrAuthor(searchText);
    }
}
