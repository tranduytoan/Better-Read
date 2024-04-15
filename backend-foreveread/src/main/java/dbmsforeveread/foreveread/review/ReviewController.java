package dbmsforeveread.foreveread.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/{bookId}")
    public ReviewDocument addReview(@PathVariable Long bookId, @RequestBody ReviewRequest reviewRequest) {
        return reviewService.addReview(bookId, reviewRequest.getUserId(), reviewRequest.getRating(),
                reviewRequest.getTitle(), reviewRequest.getComment());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Map<String, Object>> getReviewsByBookId(@PathVariable Long bookId,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        Page<ReviewDocument> reviewPage = reviewService.getReviewsByBookId(bookId, page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("content", reviewPage.getContent());
        response.put("totalElements", reviewPage.getTotalElements());
        response.put("totalPages", reviewPage.getTotalPages());
        response.put("pageNumber", reviewPage.getNumber());
        response.put("pageSize", reviewPage.getSize());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{reviewId}")
    public ReviewDocument updateReview(@PathVariable String reviewId, @RequestBody ReviewRequest reviewRequest) {
        return reviewService.updateReview(reviewId, reviewRequest.getRating(),
                reviewRequest.getTitle(), reviewRequest.getComment());
    }

    @GetMapping("/{bookId}/average-rating")
    public Double getAverageRatingByBookId(@PathVariable Long bookId) {
        return reviewService.getAverageRatingByBookId(bookId);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable String reviewId) {
        reviewService.deleteReview(reviewId);
    }
}