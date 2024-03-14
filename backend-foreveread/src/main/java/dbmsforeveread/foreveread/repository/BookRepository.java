package dbmsforeveread.foreveread.repository;

import dbmsforeveread.foreveread.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
public interface BookRepository extends MongoRepository<Book, String> {
    @Query("{'$or':[ {'title': {$regex: ?0, $options: 'i'}}, {'author': {$regex: ?0, $options: 'i'}}, {'genre': {$regex: ?0, $options: 'i'}}]}")
    List<Book> findByTitleOrAuthor(String searchText);
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

//    Page<Book> findAll(Pageable pageable);

//    ScopedValue<Object> findByAuthorKeys(String authorKey);
}
