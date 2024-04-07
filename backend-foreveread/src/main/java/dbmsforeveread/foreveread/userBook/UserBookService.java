package dbmsforeveread.foreveread.userBook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserBookService {
    @Autowired
    private UserBookRepository userBookRepository;

    public UserBook addToReadingList(String userId, String bookId) {
        List<UserBook> existingEntries = userBookRepository.findByUserIdAndStatus(userId, "WANT_TO_READ");
        boolean exists = existingEntries.stream().anyMatch(ub -> ub.getBookId().equals(bookId));
        if (exists) {
            throw new RuntimeException("Book already in want-to-read list");
        }

        UserBook newUserBook = new UserBook();
        newUserBook.setUserId(userId);
        newUserBook.setBookId(bookId);
        newUserBook.setStatus(UserBook.BookStatus.WANT_TO_READ);
        return userBookRepository.save(newUserBook);
    }

    public UserBook markAsRead(String bookId, String userId) {
        UserBook userBook = userBookRepository.findByUserIdAndBookId(userId, bookId);
        if (userBook != null) {
            userBook.setStatus(UserBook.BookStatus.READ);
            return userBookRepository.save(userBook);
        } else {
            throw new RuntimeException("Book not found in user's list");
        }
    }

    public void removeBookFromList(String bookId, String userId) {
        UserBook userBook = userBookRepository.findByUserIdAndBookId(userId, bookId);
        if (userBook != null) {
            userBookRepository.delete(userBook);
        } else {
            throw new RuntimeException("Book not found in user's list");
        }
    }

    public List<UserBook> getBooksToRead(String userId) {
        return userBookRepository.findByUserIdAndStatus(userId, String.valueOf(UserBook.BookStatus.WANT_TO_READ));
    }

    public List<UserBook> getReadBooks(String userId) {
        return userBookRepository.findByUserIdAndStatus(userId, String.valueOf(UserBook.BookStatus.READ));
    }

    public ResponseEntity<List<UserBook>> getAllUserBooks(String userId) {
        List<UserBook> userBooks = userBookRepository.findByUserId(userId);
        return ResponseEntity.ok(userBooks);
    }
}
