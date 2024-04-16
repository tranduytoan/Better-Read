package dbmsforeveread.foreveread.readingprogress;

import lombok.Data;

@Data
public class ReadingProgressRequest {
    private Long userId;
    private Long bookId;
    private ReadingStatus progress;
    private int currentPage;
}
