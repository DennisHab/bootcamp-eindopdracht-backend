package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.model.Address;
import dennis.novi.livelyEvents.model.Event;
import dennis.novi.livelyEvents.model.UserNormal;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

public interface EventService {
    List<Event> getAllEvents();
    Page<Event> getPagedEvents(int page, int size, boolean sortByName, boolean sortByCity, boolean sortByDate) throws ParseException;
    Event getEvent(long id);
    void save(Event event) throws ParseException;
    void deleteById(long id) ;
    void updateEvent(Event event, long id) ;
    Double calculateAverageRating(Event event);
    void addEventToVenue(Event event, long id, String username) ;
    List<Event> findEventByVenueName(String venueName);
    void deleteUserVenueById(String username, Long id, Long eventId);
    void uploadImageToEvent(MultipartFile file, Long id, String username);
}
