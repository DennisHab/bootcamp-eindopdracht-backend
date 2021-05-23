package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<Review> getAllReviews();
    Review getReview(Long id);
    void save(Review review);
    void deleteById(Long id);
    void updateReview(Long id, Review review);
    void updateReviewRating(Long id, Review newReview);
    void addReviewToUserAndEvent(Long id, Review review, String username);
    void addReviewToUserAndVenue(Long id, Review review, String username);
}
