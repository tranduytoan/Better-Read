package dbmsforeveread.foreveread.borrowBook;

import dbmsforeveread.foreveread.exceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/borrow")
public class BorrowingController {
    private final BorrowingService borrowingService;

    @PostMapping("/{bookId}/user/{userId}")
    public ResponseEntity<?>borrowBook(@PathVariable String bookId, @PathVariable Long userId) {
        try {
            borrowingService.borrowBook(bookId, userId);
            return ResponseEntity.ok().build();
        } catch (BookNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BorrowingDTO>> getUserBorrowings(@PathVariable Long userId) {
        List<BorrowingDTO> borrowings = borrowingService.getUserBorrowings(userId);
        return ResponseEntity.ok(borrowings);
    }
}
