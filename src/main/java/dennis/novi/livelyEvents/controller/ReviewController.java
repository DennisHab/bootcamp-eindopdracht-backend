package dennis.novi.livelyEvents.controller;

import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.Address;
import dennis.novi.livelyEvents.model.Review;
import dennis.novi.livelyEvents.model.User;
import dennis.novi.livelyEvents.model.UserNormal;
import dennis.novi.livelyEvents.repository.UserNormalRepository;
import dennis.novi.livelyEvents.repository.UserRepository;
import dennis.novi.livelyEvents.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.JobKOctets;
import java.util.List;

@RestController
public class ReviewController {
    @Autowired
    ReviewService reviewService;
    @Autowired
    UserNormalRepository userNormalRepository;

    @GetMapping(value="/reviews")
    public ResponseEntity<Object> getReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
    @GetMapping(value="/reviews/{id}")
    public ResponseEntity<Object> getReview(@PathVariable("id") long id) {
        return new ResponseEntity<>(reviewService.getReview(id), HttpStatus.OK);
    }
    @PostMapping(value = "/reviews")
    public ResponseEntity<Object> addReview(@RequestBody Review review) {
        reviewService.save(review);
        return new ResponseEntity<>("Review added", HttpStatus.CREATED);
    }
    @PostMapping(value = "users/{username}/reviews")
    public ResponseEntity<Object> addReviewToUser(@PathVariable("username") String username, @RequestBody Review review) {
        UserNormal user = userNormalRepository.findById(username).get();
        if(user.getUsername() == username && review.getUserNormal() == null) {
            review.setUserNormal(user);
            reviewService.save(review);
            return new ResponseEntity<>("Review added to user.",HttpStatus.CREATED);}
        else {
            throw new RecordNotFoundException("Either the ID requested doesn't exist or this review was already added to another user.");
        }
    }
    @PutMapping(value = "/reviews/{id}")
    public ResponseEntity<Object> updateReview(@PathVariable("id") Long id,@RequestBody Review review) {
        reviewService.updateReview(id, review);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping(value = "/reviews/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable("id") long id) {
        reviewService.deleteById(id);
        return new ResponseEntity<>("Review deleted", HttpStatus.OK);
    }
    @PatchMapping(value = "reviews/{id}")
    public ResponseEntity<Object> updateReviewRating(@PathVariable("id") long id, @RequestBody Review review) {
        reviewService.updateReviewRating(id, review);
        return ResponseEntity.noContent().build() ;
    }
}
