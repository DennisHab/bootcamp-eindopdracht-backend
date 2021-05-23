package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.*;
import dennis.novi.livelyEvents.repository.EventRepository;
import dennis.novi.livelyEvents.repository.ReviewRepository;
import dennis.novi.livelyEvents.repository.UserNormalRepository;
import dennis.novi.livelyEvents.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService{
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserNormalRepository userNormalRepository;
    @Autowired
    VenueRepository venueRepository;

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
        review.setEvent(review.getEvent());
        review.setVenue(review.getVenue());
        review.setUserNormal(review.getUserNormal());
        reviewRepository.save(review);
    }
    @Override
    public void addReviewToUserAndEvent(Long id, Review review, String username){
        Event event = eventRepository.findById(id).get();
        List<Review> reviewsEvent = event.getReviews();
        UserNormal user = userNormalRepository.findById(username).get();
        List<Review> reviewsUser = user.getReviews();
        review.setUserNormal(user);
        review.setEvent(event);
        reviewsEvent.add(review);
        reviewsUser.add(review);
        reviewRepository.save(review);
    }
    @Override
    public void addReviewToUserAndVenue(Long id, Review review, String username){
        Venue venue = venueRepository.findById(id).get();
        List<Review> reviewsVenue = venue.getReviews();
        UserNormal user = userNormalRepository.findById(username).get();
        List<Review> reviewsUser = user.getReviews();
        review.setUserNormal(user);
        review.setVenue(venue);
        reviewsVenue.add(review);
        reviewsUser.add(review);
        reviewRepository.save(review);
    }
    }


