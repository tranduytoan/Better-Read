package dbmsforeveread.foreveread.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbmsforeveread.foreveread.config.BaseRedisService;
import dbmsforeveread.foreveread.config.BaseRedisServiceImpli;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/redis")
@Service
@Component
public class BookRedisServiceImpli extends BaseRedisServiceImpli implements BookRedisService {

    private final ObjectMapper redisObjectMapper;

    @Autowired
    public BookRedisServiceImpli(RedisTemplate<String, Object> redisTemplate, ObjectMapper redisObjectMapper) {
        super(redisTemplate);
        this.redisObjectMapper = redisObjectMapper;
    }

    final String key = "Books";

    @Override
    public void addBookToRedis(BookDTO book) {
        String field = key + book.getId();
        try {

            String jsonString = redisObjectMapper.writeValueAsString(book);
            this.hashSet(key, field, jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void upadteBookToRedis(String id) {

    }

    @Override
    public void deleteBookToRedis(String id) {
        String field = key + id;
        this.delete(key, field);
    }
    // hoặc ây tuwf t đã cái delete vơới update t từ h kiểu thêm vào redis đã


    @Override
    public BookDTO getBookFromRedis(String id) {
        String field = key + id;
        // check xem co ko
        String json = (String) this.hashGet(key, field);
        BookDTO BookResponse = null;
        try {
            if (json != null) {
                BookResponse = redisObjectMapper.readValue(json, BookDTO.class);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return BookResponse;
    }

}
//    @Override
//    public void clear() {
//
//    }
//
//    @Override
//    public List<Book> getAllBooks(String keyWord, Long id) {
//        return null;
//    }
//
//    @Override
//    public void saveAllProducts(List<Book> books, String keyWord, Long id) {
//
//    }


//    @PostMapping
//    public void set(){
//        redisService.set("hehe","hihi");
//    }




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