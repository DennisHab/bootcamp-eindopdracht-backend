package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.exception.BadRequestException;
import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.exception.UsernameTakenException;
import dennis.novi.livelyEvents.model.*;
import dennis.novi.livelyEvents.repository.EventRepository;
import dennis.novi.livelyEvents.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private VenueRepository venueRepository;

    @Override
    public List<Event> getAllEvents(){
        List<Event> events = eventRepository.findAll();
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            event.setRating(calculateAverageRating(event));
        }
        return events;
    }
    @Override
    public Event getEvent(long id){
        if (eventRepository.existsById(id)) {
            Event event = eventRepository.findById(id).get();
            event.setRating(calculateAverageRating(event));
            return eventRepository.findById(id).orElse(null);
        } else {
            throw new RecordNotFoundException("This id doesn't exist: " + id);
        }
    }
    @Override
    public void save(Event event){
         {
            event.setRating(calculateAverageRating(event));
            eventRepository.save(event);}
    }
    @Override
    public void deleteById(long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);}
        else {
            throw new RecordNotFoundException("The following ID can't be deleted because it doesnt exist. ID : " + id);
        }
    }
    @Override
    public void updateEvent(Event newEvent, long id) {
        if (!eventRepository.existsById(id)) throw new RecordNotFoundException();
        Event event= eventRepository.findById(id).get();
        event.setName(newEvent.getName());
        event.setEventDescription(newEvent.getEventDescription());
        event.setTicketRequired(newEvent.isTicketRequired());
        event.setVenue(event.getVenue());
        event.setReviews(event.getReviews());
        event.setTime(newEvent.getTime());
        event.setDate(newEvent.getDate());
        event.setType(newEvent.getType());
        event.setRating(event.getRating());
        eventRepository.save(event);
    }
    @Override
    public void addEventToVenue(Event event, long id) {
        Venue venue = venueRepository.getOne(id);
        List<Event> events = venue.getEvents();
        events.add(event);
        event.setVenue(venue);
        event.setRating(calculateAverageRating(event));
        eventRepository.save(event);
    }
    @Override
    public Double calculateAverageRating(Event event) {
        List<Double> eventReviewRatings = new ArrayList<>();
        if (event.getReviews() == null){
            eventReviewRatings.add(6.0);
        } else {List<Review> eventReviews = event.getReviews();
            for (Review review : eventReviews) {
                eventReviewRatings.add(review.getRating());
            }
        }
        double totalReviewRating = 0.0;
        for (Double eventReviewRating : eventReviewRatings) {
            totalReviewRating = totalReviewRating + eventReviewRating;
        }
        if (event.getReviews() == null) {
            return 6.0; } else {
            List<Review> eventTotalReviews = event.getReviews();
            int totalReviews = eventTotalReviews.size();
            return Math.round((totalReviewRating/totalReviews) * 10.0) / 10.0;
        }
    }
}