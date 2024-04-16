package dbmsforeveread.foreveread.review;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long userId;
    private Long bookId;
    private String title;
    private String comment;
    private Integer rating;
}
