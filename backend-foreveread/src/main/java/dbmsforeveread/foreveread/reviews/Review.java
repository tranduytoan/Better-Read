package dbmsforeveread.foreveread.reviews;

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
public class Review {
    @Id
    private String id;
    private String userId;
    private String bookId;
    private String title;
    private String description;
    private int rating;
    private LocalDateTime createdAt;
}
