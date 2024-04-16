package dbmsforeveread.foreveread.review;

import dbmsforeveread.foreveread.book.Book;
import dbmsforeveread.foreveread.book.BookRepository;
import dbmsforeveread.foreveread.exceptions.ResourceNotFoundException;
import dbmsforeveread.foreveread.user.User;
import dbmsforeveread.foreveread.user.UserRepository;
import dbmsforeveread.foreveread.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewDocumentRepository reviewDocumentRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public ReviewDocument addReview(Long bookId, Long userId, Integer rating, String title, String comment) {
        Book book = bookRepository.findById(bookId).
                orElseThrow(() -> new RuntimeException("Book not found"));

        ReviewDocument reviewDocument = ReviewDocument.builder()
                .bookId(bookId)
                .userId(userId)
                .rating(rating)
                .title(title)
                .comment(comment)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return reviewDocumentRepository.save(reviewDocument);
    }

    public Page<ReviewDocument> getReviewsByBookId(Long bookId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return reviewDocumentRepository.findByBookId(bookId, pageable)
                .map(reviewDocument -> {
                    User user = userRepository.findById(reviewDocument.getUserId())
                            .orElse(null);
                    reviewDocument.setUser(user);
                    return reviewDocument;
                });
    }

    public Double getAverageRatingByBookId(Long bookId) {
        List<ReviewDocument> reviews = reviewDocumentRepository.findByBookId(bookId);
        if (reviews.isEmpty()) {
            return null;
        }
        double sum = reviews.stream().mapToInt(ReviewDocument::getRating).sum();
        return sum / reviews.size();
    }

    public ReviewDocument updateReview(String reviewId, Integer rating, String title, String comment) {
        ReviewDocument reviewDocument = reviewDocumentRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        reviewDocument.setRating(rating);
        reviewDocument.setTitle(title);
        reviewDocument.setComment(comment);
        reviewDocument.setUpdatedAt(LocalDateTime.now());
        return reviewDocumentRepository.save(reviewDocument);
    }

    public void deleteReview(String reviewId) {
        reviewDocumentRepository.deleteById(reviewId);
    }
}


