package dbmsforeveread.foreveread.book;

import lombok.Data;

@Data
public class BookRecommendedResponse {
    private Long bookId;
    private String imageUrl;
    private String title;
}
