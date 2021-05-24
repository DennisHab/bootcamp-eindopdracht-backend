package dennis.novi.livelyEvents.controller;

import dennis.novi.livelyEvents.model.Address;
import dennis.novi.livelyEvents.model.Event;
import dennis.novi.livelyEvents.model.Venue;
import dennis.novi.livelyEvents.repository.EventRepository;
import dennis.novi.livelyEvents.service.EventService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class EventController {
    @Autowired
    EventService eventService;
    @Autowired
    EventRepository eventRepository;

    @GetMapping(value = "/events")
    public ResponseEntity<Object> getEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
    //Zoek op event naam en venue naam
    @GetMapping(value="/events/search/{query}")
    public ResponseEntity<Object> searchEvent(@PathVariable("query") String query){
        List<Event> events = eventRepository.findAllByNameContainingIgnoreCase(query);
        List<Event> eventsVenue = eventService.findEventByVenueName(query);
        events.addAll(eventsVenue);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping(value="/events/{id}")
    public  ResponseEntity<Object> getEvent(@PathVariable("id") long id) {
        return new ResponseEntity<>(eventService.getEvent(id), HttpStatus.OK);
    }

    @PostMapping(value= "/events")
    public ResponseEntity<Object> createEvent(@RequestBody Event event) {
        eventService.save(event);
        return new ResponseEntity<>("Address Created", HttpStatus.CREATED);
    }
    @PostMapping(value= "/venues/{id}/events")
    public ResponseEntity<Object> addEventToVenue(@PathVariable("id") long id, @RequestBody Event event) throws ParseException {
        eventService.addEventToVenue(event, id);
        return new ResponseEntity<>("Event: " + event.getName() +" added to venue: " + id, HttpStatus.CREATED);
    }

    @PutMapping(value="/event/{id}")
    public ResponseEntity<Object> updateEvent(@PathVariable("id") long id ,@RequestBody Event event) {
        eventService.updateEvent(event, id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping(value="/event/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable("id") long id) {
        eventService.deleteById(id);
        return new ResponseEntity<>("Event deleted", HttpStatus.OK);
    }
}
