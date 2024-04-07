//package dbmsforeveread.foreveread.book;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import dbmsforeveread.foreveread.book.Book;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BookRedisService {
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
//    public void clear() {
//        if (!useRedisCache) return;
//        redisTemplate.getConnectionFactory().getConnection().flushDb();
//    }
//
//
//    public List<Book> getAllBooks(String name, PageRequest pageRequest) throws JsonProcessingException {
//        if (!useRedisCache) return null;
//        String key = getKey(name, pageRequest);
//        String json = redisTemplate.opsForValue().get(key);
//        return json != null ? objectMapper.readValue(json, new TypeReference<List<Book>>() {}) : null;
//    }
//
//
//    @Async
//    public void saveBooksAsync(List<Book> books, String name, PageRequest pageRequest) throws JsonProcessingException {
//        if (!useRedisCache) return;
//        String key = getKey(name, pageRequest);
//        String json = objectMapper.writeValueAsString(books);
//        redisTemplate.opsForValue().set(key, json);
//        logger.info("Asynchronously saved books to Redis with key: {}", key);
//    }
//    @Async
////    public void saveBooksAsync(List<Book> books, String name, PageRequest pageRequest) throws JsonProcessingException {
////        if (!useRedisCache) return;
////        String key = getKey(name, pageRequest);
////        Map<String, String> bookMap = books.stream()
////                .collect(Collectors.toMap(
////                        Book::getId,
////                        book -> {
////                            try {
////                                return objectMapper.writeValueAsString(book);
////                            } catch (JsonProcessingException e) {
////                                logger.error("error serializing books to Json", e);
////                                return null;
////                            }
////                        }
////                ));
////    }
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
//
////package dbmsforeveread.foreveread.service;
////
////import com.fasterxml.jackson.core.JsonProcessingException;
////import com.fasterxml.jackson.core.type.TypeReference;
////import com.fasterxml.jackson.databind.ObjectMapper;
////import dbmsforeveread.foreveread.book.Book;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.data.domain.PageRequest;
////import org.springframework.data.redis.core.HashOperations;
////import org.springframework.data.redis.core.RedisTemplate;
////import org.springframework.scheduling.annotation.Async;
////import org.springframework.stereotype.Service;
////
////import javax.annotation.PostConstruct;
////import java.util.List;
////import java.util.Map;
////import java.util.stream.Collectors;
////
////@Service
////public class BookRedisService {
////    private static final Logger logger = LoggerFactory.getLogger(BookRedisService.class);
////
////    private final RedisTemplate<String, String> redisReadTemplate;
////    private final RedisTemplate<String, String> redisWriteTemplate;
////    private final ObjectMapper objectMapper;
////
////    private HashOperations<String, String, String> hashOperations;
////
////    @Value("${spring.data.redis.use-redis-cache}")
////    private boolean useRedisCache;
////
////    @Autowired
////    public BookRedisService(RedisTemplate<String, String> redisReadTemplate,
////                            RedisTemplate<String, String> redisWriteTemplate,
////                            ObjectMapper objectMapper) {
////        this.redisReadTemplate = redisReadTemplate;
////        this.redisWriteTemplate = redisWriteTemplate;
////        this.objectMapper = objectMapper;
////    }
////
////    @PostConstruct
////    public void init() {
////        hashOperations = redisWriteTemplate.opsForHash();
////    }
////
////    private String getKey(String name, PageRequest pageRequest) {
////        return "books:" + name + ":" + pageRequest.getPageNumber() + ":" + pageRequest.getPageSize();
////    }
////
////    public void clear() {
////        if (!useRedisCache) return;
////        redisWriteTemplate.getConnectionFactory().getConnection().flushDb();
////    }
////
////    public List<Book> getAllBooks(String name, PageRequest pageRequest) throws JsonProcessingException {
////        if (!useRedisCache) return null;
////        String key = getKey(name, pageRequest);
////        Map<String, String> bookMap = hashOperations.entries(key);
////        return bookMap.values().stream()
////                .map(json -> {
////                    try {
////                        return objectMapper.readValue(json, Book.class);
////                    } catch (JsonProcessingException e) {
////                        logger.error("Error deserializing book JSON", e);
////                        return null;
////                    }
////                })
////                .collect(Collectors.toList());
////    }
////
////    @Async
////    public void saveBooksAsync(List<Book> books, String name, PageRequest pageRequest) throws JsonProcessingException {
////        if (!useRedisCache) return;
////        String key = getKey(name, pageRequest);
////        Map<String, String> bookMap = books.stream()
////                .collect(Collectors.toMap(
////                        Book::getId,
////                        book -> {
////                            try {
////                                return objectMapper.writeValueAsString(book);
////                            } catch (JsonProcessingException e) {
////                                logger.error("Error serializing book to JSON", e);
////                                return null;
////                            }
////                        }
////                ));
////        hashOperations.putAll(key, bookMap);
////        logger.info("Asynchronously saved books to Redis with key: {}", key);
////    }
////
////    public Book getBook(String id) throws JsonProcessingException {
////        if (!useRedisCache) return null;
////        String json = (String) redisReadTemplate.opsForHash().get("books", id);
////        return json != null ? objectMapper.readValue(json, Book.class) : null;
////    }
////
////    public void saveBook(Book book) throws JsonProcessingException {
////        if (!useRedisCache) return;
////        String json = objectMapper.writeValueAsString(book);
////        redisWriteTemplate.opsForHash().put("books", book.getId(), json);
////    }
////}