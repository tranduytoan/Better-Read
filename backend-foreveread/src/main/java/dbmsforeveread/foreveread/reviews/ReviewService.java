package dbmsforeveread.foreveread.reviews;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    public Review saveReview(Review review) {
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByBookId(String bookId) {
        return reviewRepository.findByBookId(bookId);

    }

    public double getAverageRatingByBookId(String bookId) {
        List<Review> reviews = getReviewsByBookId(bookId);
        if (reviews.isEmpty()) {
            return 0;
        }
        int totalRating = reviews.stream().mapToInt(Review::getRating).sum();
        return (double) totalRating / reviews.size();
    }
}


