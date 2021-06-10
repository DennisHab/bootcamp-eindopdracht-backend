package dennis.novi.livelyEvents.controller;

import dennis.novi.livelyEvents.exception.NotAuthorizedException;
import dennis.novi.livelyEvents.model.Event;
import dennis.novi.livelyEvents.repository.EventRepository;
import dennis.novi.livelyEvents.service.EventService;
import dennis.novi.livelyEvents.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge=3600)
public class EventController {
    @Autowired
    EventService eventService;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    FileUploadService fileUploadService;

    @GetMapping(value = "/events")
    public ResponseEntity<Object> getEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
    @GetMapping(value="/events/{id}")
    public  ResponseEntity<Object> getEvent(@PathVariable("id") long id) {
        return new ResponseEntity<>(eventService.getEvent(id), HttpStatus.OK);
    }
    @PostMapping(value= "userOwner/{username}/venues/{id}/events")
    public ResponseEntity<Object> addEventToOwnerVenue(@PathVariable("id") long id, @PathVariable("username") String username, @RequestBody Event event, Principal principal) {
        if(principal.getName().equals(username)){
        eventService.addEventToVenue(event, id);
        return new ResponseEntity<>("Event: " + event.getName() +" added to venue: " + id, HttpStatus.CREATED);}
        else throw new NotAuthorizedException("You are not authorized to make this request");
    }
    @PostMapping(value="{username}/event/{eventId}/uploadimage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addImageToEvent(@PathVariable(value= "username") String username, @PathVariable(value = "eventId") Long eventId, @RequestParam("file") MultipartFile file, Principal principal ){
        if (principal.getName().equals(username)){
            fileUploadService.uploadImageToEvent(file, eventId);
            return new ResponseEntity<>("File uploaded", HttpStatus.OK);}
        else throw new NotAuthorizedException("You are not authorized to make this request");
    }
    @DeleteMapping(value="/userOwner/{username}/venues/{venueId}/{eventId}")
    public ResponseEntity<Object> deleteEventFromOwnerVenue(@PathVariable("venueId") Long id, @PathVariable("username") String username, @PathVariable("eventId") Long eventId, Principal principal) {
        if(principal.getName().equals(username)){
        eventService.deleteUserVenueById(username, id, eventId);
        return new ResponseEntity<>("Event deleted", HttpStatus.OK);}
        else throw new NotAuthorizedException("You are not authorized to make this request");
    }
}
