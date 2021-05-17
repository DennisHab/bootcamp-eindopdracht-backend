package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.Review;
import dennis.novi.livelyEvents.model.User;
import dennis.novi.livelyEvents.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService{
    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }
    @Override
    public Review getReview(Long id) {
        if (!reviewRepository.existsById(id)) throw new RecordNotFoundException("Review doesn't exist");
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Review review) { reviewRepository.save(review);}

    @Override
    public void deleteById(Long id) {
        if(!reviewRepository.existsById(id)) throw new RecordNotFoundException("Review doesn't exist");
        reviewRepository.deleteById(id);
    }
    @Override
    public void updateReview(Long id, Review newReview) {
        if (!reviewRepository.existsById(id)) throw new RecordNotFoundException();
        Review review = reviewRepository.findById(id).get();
        review.setReviewContent(newReview.getReviewContent());
        review.setReviewRating(newReview.getReviewRating());
        reviewRepository.save(review);
    }
    @Override
    public void updateReviewRating(Long id, Review newReview) {
        if (!reviewRepository.existsById(id)) throw new RecordNotFoundException();
        Review review = reviewRepository.findById(id).get();
        review.setReviewRating(newReview.getReviewRating());
        review.setReviewContent(review.getReviewContent());
        reviewRepository.save(review);
    }
    }


