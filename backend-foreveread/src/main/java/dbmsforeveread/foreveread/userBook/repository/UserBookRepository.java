package dbmsforeveread.foreveread.userBook.repository;

import dbmsforeveread.foreveread.userBook.UserBook;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserBookRepository extends MongoRepository<UserBook, String> {
    List<UserBook> findByUserIdAndStatus(String userId, String status);

    UserBook findByUserIdAndBookId(String userId, String bookId);
}