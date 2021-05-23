package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.model.Address;
import dennis.novi.livelyEvents.model.Event;
import dennis.novi.livelyEvents.model.UserNormal;

import java.text.ParseException;
import java.util.List;

public interface EventService {
    List<Event> getAllEvents();
    Event getEvent(long id);
    void save(Event event);
    void deleteById(long id) ;
    void updateEvent(Event event, long id);
    Double calculateAverageRating(Event event);
    void addEventToVenue(Event event, long id);
}
