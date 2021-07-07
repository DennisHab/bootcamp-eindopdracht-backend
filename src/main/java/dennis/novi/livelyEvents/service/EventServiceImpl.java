package dennis.novi.livelyEvents.service;
import dennis.novi.livelyEvents.exception.FileStorageException;
import dennis.novi.livelyEvents.exception.NotAuthorizedException;
import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.*;
import dennis.novi.livelyEvents.repository.EventRepository;
import dennis.novi.livelyEvents.repository.UserOwnerRepository;
import dennis.novi.livelyEvents.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Value("${app.upload.dir:${C:\\Users\\DennisHabets\\WebstormProjects\\novi-eindopdracht-lively\\public}}")
    public String uploadDir;
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
    public Page<Event> getPagedEvents(int page, int size, boolean SortByName, boolean SortByCity, boolean SortByDate)  {
        Pageable requestedPage = PageRequest.of(page, size);
        List<Event> allEvents = eventRepository.findAll();
        if(SortByName){requestedPage = PageRequest.of(page, size, Sort.by("name"));}
        if(SortByCity){requestedPage = PageRequest.of(page, size, Sort.by("venue.address.city"));}
        if(SortByDate){requestedPage = PageRequest.of(page, size, Sort.by("date"));}

        Page<Event> events = eventRepository.findAll(requestedPage);

        events.forEach(event -> event.setRating(calculateAverageRating(event)));
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
    public void save(Event event) {
            event.setRating(calculateAverageRating(event));
            eventRepository.save(event);
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
    public void updateEvent(Event newEvent, long id){
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
    public void addEventToVenue(Event event, long id, String username) {
        UserOwner userOwner = userOwnerRepository.findById(username).get();
        List<Venue> userVenues = userOwner.getVenueList();
        List<Long> userVenueIds = new ArrayList<>();
        userVenues.forEach(venue -> userVenueIds.add(venue.getId()));
        if(userVenueIds.contains(id)){
        Venue venue = venueRepository.getOne(id);
        List<Event> events = venue.getEvents();
        events.add(event);
        event.setVenue(venue);
        event.setRating(calculateAverageRating(event));
        eventRepository.save(event);}
        else throw new NotAuthorizedException("This venue is not yours. Please change venue ID");
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
        } else {throw new NotAuthorizedException("This venue is not yours. Please change venue ID");}
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
        }
        else {List<Review> eventReviews = event.getReviews();
            for (Review review : eventReviews) {
                eventReviewRatings.add(review.getRating());
            }
        }
        double totalReviewRating = 0.0;
        for (Double eventReviewRating : eventReviewRatings) {
            totalReviewRating = totalReviewRating + eventReviewRating;
        }
        if (event.getReviews() == null) {
            return 6.0; }
        else {
            List<Review> eventTotalReviews = event.getReviews();
            int totalReviews = eventTotalReviews.size();
            return Math.round((totalReviewRating/totalReviews) * 10.0) / 10.0;
        }
    }
    public void uploadImageToEvent(MultipartFile file, Long id, String username) {
        UserOwner userOwner = userOwnerRepository.findById(username).get();
        List<Venue> userVenues = userOwner.getVenueList();
        List<Event> userVenueEvents = new ArrayList<>();
        userVenues.forEach(venue -> userVenueEvents.addAll(venue.getEvents()));
        List<Long> userEventIds = new ArrayList<>();
        userVenueEvents.forEach(event -> userEventIds.add(event.getId()));
        if(userEventIds.contains(id)){
            try {
                Path copyLocation = Paths
                        .get(uploadDir +  File.separator + StringUtils.cleanPath("event" + id + file.getOriginalFilename()));
                Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
                Event event = eventRepository.findById(id).get();
                event.setImage("event" + id + file.getOriginalFilename());
                eventRepository.save(event);

            } catch (Exception e) {
                e.printStackTrace();
                throw new FileStorageException("Could not store file " + file.getOriginalFilename()
                        + ". Please try again!");
            }} else throw new NotAuthorizedException("This event is not yours.");
    }
}

