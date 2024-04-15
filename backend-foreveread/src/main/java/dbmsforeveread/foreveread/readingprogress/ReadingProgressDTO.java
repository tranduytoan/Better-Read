package dbmsforeveread.foreveread.readingprogress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadingProgressDTO {
    private Long userId;
    private Long bookId;
    private String bookTitle;
    private String bookImageUrl;
    private ReadingStatus progress;
}
