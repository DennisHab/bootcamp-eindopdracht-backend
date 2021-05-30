package dennis.novi.livelyEvents.controller;

import dennis.novi.livelyEvents.exception.NotAuthorizedException;
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
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge=3600)
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
    @PostMapping(value = "user/{username}/reviews/event/{eventId}")
    public ResponseEntity<Object> addReviewToUserAndEvent(@PathVariable("username") String username, @PathVariable("eventId") long id, @RequestBody Review review, Principal principal){
        if(principal.getName().equals(username)){
        reviewService.addReviewToUserAndEvent(id, review, username);
        return new ResponseEntity<>("Review added to user and event", HttpStatus.CREATED);}
        else throw new NotAuthorizedException("You are not authorized to make this request");
    }
    @PostMapping(value = "user/{username}/reviews/venue/{venueId}")
    public ResponseEntity<Object> addReviewToUserAndVenue(@PathVariable("username") String username, @PathVariable("venueId") long id, @RequestBody Review review, Principal principal){
        if(principal.getName().equals(username)){
        reviewService.addReviewToUserAndVenue(id, review, username);
        return new ResponseEntity<>("Review added to user and venue", HttpStatus.CREATED);}
        else throw new NotAuthorizedException("You are not authorized to make this request");
    }

}
