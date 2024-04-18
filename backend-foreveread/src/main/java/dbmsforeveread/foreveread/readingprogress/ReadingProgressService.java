package dbmsforeveread.foreveread.readingprogress;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ReadingProgressService {
    Page<ReadingProgressDTO> getReadingProgressByUserId(Long userId, Pageable pageable);
    void updateReadingProgress(Long bookId, Long userId, ReadingStatus progress, int currentPage);
    ReadingProgressDTO addBookToReadingProgress(Long bookId, Long userId);
    List<ReadingProgressDTO> getInProgressBooksByUserId(Long userId);
    List<ReadingProgressDTO> getCompletedBooksByUserId(Long userId);

    void removeBookFromReadingProgress(Long bookId, Long userId);
}
