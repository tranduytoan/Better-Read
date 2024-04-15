package dbmsforeveread.foreveread.review;

import dbmsforeveread.foreveread.user.User;
import dbmsforeveread.foreveread.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("reviews")
public class ReviewDocument {
    @Id
    private String id;
    private Long userId;
    private Long bookId;
    private String title;
    private String comment;
    private Integer rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User user;
}
