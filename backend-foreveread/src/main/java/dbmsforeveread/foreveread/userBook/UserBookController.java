package dbmsforeveread.foreveread.userBook;

import dbmsforeveread.foreveread.userBook.UserBook;
import dbmsforeveread.foreveread.userBook.UserBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/userbooks")
public class UserBookController {

    @Autowired
    private UserBookService userBookService;

    @PostMapping("/{userId}/reading-list")
    public ResponseEntity<Void> addBookToWantToRead(@Valid @PathVariable String userId, @RequestBody Map<String, String> requestBody) {
        String bookId = requestBody.get("bookId");
        userBookService.addToReadingList(userId, bookId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{bookId}/mark-as-read")
    public UserBook markAsRead(@PathVariable String bookId, @Valid @RequestBody UserBook userBook) {
        return userBookService.markAsRead(bookId, userBook.getUserId());
    }

    @DeleteMapping("/{bookId}")
    public void removeBookFromList(@PathVariable String bookId, @RequestBody UserBook userBook) {
        userBookService.removeBookFromList(bookId, userBook.getUserId());
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<UserBook>> getAllUserBooks(@PathVariable String userId) {
        return userBookService.getAllUserBooks(userId);
    }

    @GetMapping("/want-to-read/{userId}")
    public List<UserBook> getBooksToRead(@PathVariable String userId) {
        return userBookService.getBooksToRead(userId);
    }

    @GetMapping("/read/{userId}")
    public List<UserBook> getReadBooks(@PathVariable String userId) {
        return userBookService.getReadBooks(userId);
    }
}