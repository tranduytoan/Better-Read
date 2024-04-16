package dbmsforeveread.foreveread.readingprogress;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reading-progress")
@RequiredArgsConstructor
public class ReadingProgressController {
    private final ReadingProgressService readingProgressService;

    @PostMapping
    public ResponseEntity<ReadingProgressDTO> addBookToReadingProgress(@RequestBody ReadingProgressRequest request) {
        try {
            ReadingProgressDTO readingProgress = readingProgressService.addBookToReadingProgress(request.getBookId(), request.getUserId());
            return ResponseEntity.ok(readingProgress);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<Page<ReadingProgressDTO>> getReadingProgressByUserId(
            @RequestParam Long userId,
            Pageable pageable
    ) {
        Page<ReadingProgressDTO> readingProgressPage = readingProgressService.getReadingProgressByUserId(userId, pageable);
        return ResponseEntity.ok(readingProgressPage);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Void> updateReadingProgress(
            @PathVariable Long bookId,
            @RequestBody ReadingProgressRequest request) {
        readingProgressService.updateReadingProgress(bookId, request.getUserId(), request.getProgress(), request.getCurrentPage());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/in-progress")
    public ResponseEntity<List<ReadingProgressDTO>> getInProgressBooksByUserId(@RequestParam Long userId) {
        List<ReadingProgressDTO> inProgressBooks = readingProgressService.getInProgressBooksByUserId(userId);
        return ResponseEntity.ok(inProgressBooks);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<ReadingProgressDTO>> getCompletedBooksByUserId(@RequestParam Long userId) {
        List<ReadingProgressDTO> completedBooks = readingProgressService.getCompletedBooksByUserId(userId);
        return ResponseEntity.ok(completedBooks);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> removeBookFromReadingProgress(@PathVariable Long bookId, @RequestParam Long userId) {
        readingProgressService.removeBookFromReadingProgress(bookId, userId);
        return ResponseEntity.ok().build();
    }
}
