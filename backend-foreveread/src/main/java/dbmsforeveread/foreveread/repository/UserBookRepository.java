package dbmsforeveread.foreveread.repository;

import dbmsforeveread.foreveread.model.UserBook;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserBookRepository extends MongoRepository<UserBook, String> {
    List<UserBook> findByUserIdAndStatus(String userId, String status);

    UserBook findByUserIdAndBookId(String userId, String bookId);
}