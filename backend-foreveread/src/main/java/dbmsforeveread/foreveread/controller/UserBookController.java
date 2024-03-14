package dbmsforeveread.foreveread.controller;

import dbmsforeveread.foreveread.model.UserBook;
import dbmsforeveread.foreveread.service.UserBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userBooks")
public class UserBookController {

    @Autowired
    private UserBookService userBookService;

    @PostMapping
    public UserBook addBookToWantToRead(@Valid @RequestBody UserBook userBook) {
        return userBookService.addBookToWantToRead(userBook.getBookId(), userBook.getUserId());
    }

    @PutMapping("/{bookId}/mark-as-read")
    public UserBook markAsRead(@PathVariable String bookId, @Valid @RequestBody UserBook userBook) {
        return userBookService.markAsRead(bookId, userBook.getUserId());
    }

    @DeleteMapping("/{bookId}")
    public void removeBookFromList(@PathVariable String bookId, @RequestBody UserBook userBook) {
        userBookService.removeBookFromList(bookId, userBook.getUserId());
    }

    @GetMapping()
    public List<UserBook> getAllUserBooks() {
        return userBookService.getAllUserBooks();
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