package dennis.novi.livelyEvents.controller;

import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.Address;
import dennis.novi.livelyEvents.model.User;
import dennis.novi.livelyEvents.model.UserOwner;
import dennis.novi.livelyEvents.model.Venue;
import dennis.novi.livelyEvents.repository.UserOwnerRepository;
import dennis.novi.livelyEvents.repository.UserRepository;
import dennis.novi.livelyEvents.service.VenueService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VenueController {
    @Autowired
    private VenueService venueService;
    @Autowired
    private UserOwnerRepository userOwnerRepository;

    @GetMapping(value="/venues")
    public ResponseEntity<Object> getVenues(){
        List<Venue> venues = venueService.getAllVenues();
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    @GetMapping(value = "/venues/venueName/{venueName}")
    public ResponseEntity<Object> getVenuesByVenueName(@PathVariable("venueName") String venueName) {
        List<Venue> venues = venueService.getVenueVenueNameStartsWith(venueName);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    @GetMapping(value= "/venues/{id}")
    public ResponseEntity<Object> getVenue(@PathVariable("id") Long id) {
        return new ResponseEntity<>(venueService.getVenue(id), HttpStatus.OK);
    }

    @PostMapping(value = "/venues")
    public ResponseEntity<Object> createVenue(@RequestBody Venue venue) {
        venueService.save(venue);
        return new ResponseEntity<>("Venue created", HttpStatus.CREATED);
    }
    @GetMapping(value="/usersOwner/{username}/venues")
    public ResponseEntity<Object> getUserAddress(@PathVariable String username) {
        return new ResponseEntity<>(userOwnerRepository.findById(username), HttpStatus.OK);
    }

    @PostMapping(value= "/usersOwner/{username}/venues")
    public ResponseEntity<Object> addUserVenue(@PathVariable String username, @RequestBody @Validated Venue venue) {
        UserOwner userOwner = userOwnerRepository.findById(username).get();
        if(userOwner.getUsername() == username && venue.getUserOwner() == null) {
            List<Venue> userOwnerVenues = userOwner.getVenueList();
            userOwnerVenues.add(venue);
            venue.setUserOwner(userOwner);
            venueService.save(venue);
            return new ResponseEntity<>("Venue Created",HttpStatus.CREATED);}
        else {
            throw new RecordNotFoundException("Either the user doesn't exist or the venue already has an owner");
        }
    }





}
