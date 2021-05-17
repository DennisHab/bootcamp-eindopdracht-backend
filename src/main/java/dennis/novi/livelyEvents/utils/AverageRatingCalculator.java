package dennis.novi.livelyEvents.utils;

import dennis.novi.livelyEvents.model.Review;
import dennis.novi.livelyEvents.model.UserNormal;
import dennis.novi.livelyEvents.repository.ReviewRepository;
import dennis.novi.livelyEvents.repository.UserNormalRepository;
import dennis.novi.livelyEvents.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public class AverageRatingCalculator {
    @Autowired
    UserNormalRepository userNormalRepository;
    @Autowired
    ReviewRepository reviewRepository;

    public List<Double> reviewRatings(String username) {
        UserNormal user = userNormalRepository.findById(username).get();
        List<Review> userReviews = user.getReviews();
        List<Double> userReviewRatings = new ArrayList<>();
        for (int i = 0; i < userReviews.size(); i++) {
            Review review = userReviews.get(i);
            userReviewRatings.add(review.getReviewRating());
        }
        return userReviewRatings;
    }
    public Double calculateAverageRating(Long id, String username ) {
        List<Double> reviews = reviewRatings(username);
        double totalReviewRating = 0.0;
        for (int i = 1; i < reviews.size() ; i++) {
            totalReviewRating = totalReviewRating + i;
        }
        UserNormal user = userNormalRepository.findById(username).get();
        List<Review> userReviews = user.getReviews();
        Integer firstNumber = userReviews.size();
        double AverageRating = totalReviewRating / firstNumber;
        return AverageRating;
    }
}
