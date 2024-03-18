package dbmsforeveread.foreveread.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbmsforeveread.foreveread.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//public class BookRedisService implements IBookRedisService {
//    private static final Logger logger = LoggerFactory.getLogger(BookRedisService.class);
//
//    private final RedisTemplate<String, String> redisTemplate;
//    private final ObjectMapper objectMapper;
//
//    @Value("${spring.data.redis.use-redis-cache}")
//    private boolean useRedisCache;
//
//    @Autowired
//    public BookRedisService(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
//        this.redisTemplate = redisTemplate;
//        this.objectMapper = objectMapper;
//    }
//
//    private String getKey(String name, PageRequest pageRequest) {
//        return "books:" + name + ":" + pageRequest.getPageNumber() + ":" + pageRequest.getPageSize();
//    }
//
//    @Override
//    public void clear() {
//        if (!useRedisCache) return;
//        redisTemplate.getConnectionFactory().getConnection().flushDb();
//    }
//
//    @Override
//    public List<Book> getAllBooks(String name, PageRequest pageRequest) throws JsonProcessingException {
//        if (!useRedisCache) return null;
//        String key = getKey(name, pageRequest);
//        String json = redisTemplate.opsForValue().get(key);
//        return json != null ? objectMapper.readValue(json, new TypeReference<List<Book>>() {}) : null;
//    }
//
//    @Override
//    @Async
//    public void saveBooksAsync(List<Book> books, String name, PageRequest pageRequest) throws JsonProcessingException {
//        if (!useRedisCache) return;
//        String key = getKey(name, pageRequest);
//        String json = objectMapper.writeValueAsString(books);
//        redisTemplate.opsForValue().set(key, json);
//        logger.info("Asynchronously saved books to Redis with key: {}", key);
//    }
//
//    public Book getBook(String id) throws JsonProcessingException{
//        if (!useRedisCache) return null;
//        String json = redisTemplate.opsForValue().get(id);
//        return json != null ? objectMapper.readValue(json, Book.class) : null;
//    }
//
//    public void saveBook(Book book) throws JsonProcessingException {
//        if (!useRedisCache) return;
//        String json = objectMapper.writeValueAsString(book);
//        redisTemplate.opsForValue().set(book.getId(), json);
//    }
//}

@Service
public class BookRedisService {
    private static final Logger logger = LoggerFactory.getLogger(BookRedisService.class);

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.data.redis.use-redis-cache}")
    private boolean useRedisCache;

    @Autowired
    public BookRedisService(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    private String getKey(String name, PageRequest pageRequest) {
        return "books:" + name + ":" + pageRequest.getPageNumber() + ":" + pageRequest.getPageSize();
    }

    public void clear() {
        if (!useRedisCache) return;
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }


    public List<Book> getAllBooks(String name, PageRequest pageRequest) throws JsonProcessingException {
        if (!useRedisCache) return null;
        String key = getKey(name, pageRequest);
        String json = redisTemplate.opsForValue().get(key);
        return json != null ? objectMapper.readValue(json, new TypeReference<List<Book>>() {}) : null;
    }


    @Async
    public void saveBooksAsync(List<Book> books, String name, PageRequest pageRequest) throws JsonProcessingException {
        if (!useRedisCache) return;
        String key = getKey(name, pageRequest);
        String json = objectMapper.writeValueAsString(books);
        redisTemplate.opsForValue().set(key, json);
        logger.info("Asynchronously saved books to Redis with key: {}", key);
    }

    public Book getBook(String id) throws JsonProcessingException{
        if (!useRedisCache) return null;
        String json = redisTemplate.opsForValue().get(id);
        return json != null ? objectMapper.readValue(json, Book.class) : null;
    }

    public void saveBook(Book book) throws JsonProcessingException {
        if (!useRedisCache) return;
        String json = objectMapper.writeValueAsString(book);
        redisTemplate.opsForValue().set(book.getId(), json);
    }
}