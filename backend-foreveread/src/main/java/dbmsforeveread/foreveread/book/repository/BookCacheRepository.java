package dbmsforeveread.foreveread.book.repository;

import dbmsforeveread.foreveread.book.model.Book;

import java.util.Optional;

public interface BookCacheRepository {
    void saveBook(Book book);
    Optional<Book> findBookById(String id);
    void updateBook(Book book);
    void deleteBookById(String id);
}