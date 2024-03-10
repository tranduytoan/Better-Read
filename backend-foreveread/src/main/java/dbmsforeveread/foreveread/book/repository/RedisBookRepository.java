package dbmsforeveread.foreveread.book.repository;

import dbmsforeveread.foreveread.book.model.Book;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RedisBookRepository implements BookCacheRepository{
    private static final String BOOK_KEY = "Book";
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisBookRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveBook(Book book) {
        redisTemplate.opsForHash().put(BOOK_KEY, book.getId(), book);
    }

    @Override
    public Optional<Book> findBookById(String id) {
        return Optional.ofNullable((Book) redisTemplate.opsForHash().get(BOOK_KEY, id));
    }

    @Override
    public void updateBook(Book book) {
        saveBook(book);
    }

    @Override
    public void deleteBookById(String id) {
        redisTemplate.opsForHash().delete(BOOK_KEY, id);
    }
}