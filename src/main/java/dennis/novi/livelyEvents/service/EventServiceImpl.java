package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.exception.BadRequestException;
import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.exception.UsernameTakenException;
import dennis.novi.livelyEvents.model.*;
import dennis.novi.livelyEvents.repository.EventRepository;
import dennis.novi.livelyEvents.repository.UserOwnerRepository;
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
    @Autowired
    private UserOwnerRepository userOwnerRepository;

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
            String date = event.getDate();
            String[] dateSplit = date.split("-");
            String day = dateSplit[2];
            String month = dateSplit[1];
            String year = dateSplit[0];
            String newDate = day+"-"+month+"-"+year;
            event.setDate(newDate);
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
        String date = event.getDate();
        String[] dateSplit = date.split("-");
        String day = dateSplit[2];
        String month = dateSplit[1];
        String year = dateSplit[0];
        String newDate = day+"-"+month+"-"+year;
        event.setName(newEvent.getName());
        event.setEventDescription(newEvent.getEventDescription());
        event.setTicketRequired(newEvent.isTicketRequired());
        event.setVenue(event.getVenue());
        event.setReviews(event.getReviews());
        event.setTime(newEvent.getTime());
        event.setDate(newDate);
        event.setType(newEvent.getType());
        event.setRating(event.getRating());
        eventRepository.save(event);
    }
    @Override
    public void addEventToVenue(Event event, long id) {
        Venue venue = venueRepository.getOne(id);
        List<Event> events = venue.getEvents();
        String date = event.getDate();
        String[] dateSplit = date.split("-");
        String day = dateSplit[2];
        String month = dateSplit[1];
        String year = dateSplit[0];
        String newDate = day+"-"+month+"-"+year;
        event.setDate(newDate);
        events.add(event);
        event.setVenue(venue);
        event.setRating(calculateAverageRating(event));
        eventRepository.save(event);
    }
    @Override
    public void deleteUserVenueById(String username, Long id, Long eventId){
        UserOwner user = userOwnerRepository.findById(username).get();
        Venue userVenue = venueRepository.findById(id).get();
        Event event = eventRepository.findById(eventId).get();
        List<Venue> userVenues = user.getVenueList();
        List<Long> userVenueId = new ArrayList<>();
        List<Event> userEvents = userVenue.getEvents();
        for (Venue venue : userVenues) {
            userVenueId.add(venue.getId());
        }
        if (userVenueId.contains(id)){
            userEvents.remove(event);
            eventRepository.deleteById(eventId);
        } else {throw new BadRequestException("Id doesn't belong to user");}
    }
    @Override
    public List<Event> findEventByVenueName(String venueName){
        List<Event> events = eventRepository.findAll();
        List<Venue> eventsVenues = new ArrayList<>();
        List<Event> results = new ArrayList<>();
        for (Event event : events) {
            eventsVenues.add(event.getVenue());
        }
        for (Venue venue : eventsVenues) {
            if (venue.getVenueName().contains(venueName)) {
                results.addAll(venue.getEvents());
            }
        }
        return results;
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
