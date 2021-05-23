package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.Event;
import dennis.novi.livelyEvents.model.Review;
import dennis.novi.livelyEvents.model.UserOwner;
import dennis.novi.livelyEvents.model.Venue;
import dennis.novi.livelyEvents.repository.UserOwnerRepository;
import dennis.novi.livelyEvents.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    VenueRepository venueRepository;
    @Autowired
    UserOwnerRepository userOwnerRepository;

    @Override
    public List<Venue> getAllVenues() {
        List<Venue> venues = venueRepository.findAll();
        for (int i = 0; i < venues.size(); i++) {
            Venue venue = venues.get(i);
            venue.setRating(calculateAverageRating(venue));
        }
        return venues;
    }
    @Override
    public Venue getVenue(Long id) {
        if (venueRepository.existsById(id)) {
            Venue venue = venueRepository.findById(id).get();
            venue.setRating(calculateAverageRating(venue));
            return venue;
        } else {
            throw new RecordNotFoundException("This id doesn't exist: " + id);
        }
    }
    @Override
    public List<Venue> getVenueVenueNameStartsWith(String venueName){
        return venueRepository.findAllByVenueNameStartingWith(venueName);
    }
    @Override
    public void save(Venue venue) {
        venue.setRating(calculateAverageRating(venue));
        venueRepository.save(venue);
    }
    @Override
    public void deleteById(Long id) {
        venueRepository.deleteById(id);
    }
    @Override
    public Optional<Venue> getVenueByVenueName(String venueName) {
        if (venueRepository.existsVenueByVenueName(venueName)) {
            return venueRepository.findByVenueName(venueName);
        } else {
            throw new RecordNotFoundException("This venue doesn't exist");
        }
    }
    @Override
    public Long getUserVenueId(String username) {
    UserOwner user = userOwnerRepository.findById(username).get();
    List<Venue> venues = user.getVenueList();
    long venueId = 0;
    for (int i = 0; i < venues.size(); i++) {
        Venue venue = venues.get(i);
        venueId = venue.getId();
    } return venueId;}

    @Override
    public Double calculateAverageRating(Venue venue) {
        List<Double> venueReviewRatings = new ArrayList<>();
        if (venue.getReviews() == null){
            venueReviewRatings.add(6.0);
        } else {List<Review> venueReviews = venue.getReviews();
            for (Review review : venueReviews) {
                venueReviewRatings.add(review.getRating());
            }
        }
        double totalReviewRating = 0.0;
        for (Double venueReviewRating : venueReviewRatings) {
            totalReviewRating = totalReviewRating + venueReviewRating;
        }
        if (venue.getReviews() == null) {
            return 6.0; } else {
            List<Review> venueTotalReviews = venue.getReviews();
            int totalReviews = venueTotalReviews.size();
            return Math.round((totalReviewRating/totalReviews) * 10.0) / 10.0;
        }
    }
}
