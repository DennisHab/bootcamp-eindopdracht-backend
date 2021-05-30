package dennis.novi.livelyEvents.controller;

import dennis.novi.livelyEvents.exception.NotAuthorizedException;
import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.*;
import dennis.novi.livelyEvents.repository.UserOwnerRepository;
import dennis.novi.livelyEvents.repository.UserRepository;
import dennis.novi.livelyEvents.repository.VenueRepository;
import dennis.novi.livelyEvents.service.VenueService;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge=3600)
public class VenueController {
    @Autowired
    private VenueService venueService;
    @Autowired
    private UserOwnerRepository userOwnerRepository;
    @Autowired
    private VenueRepository venueRepository;

    @GetMapping(value="/venues")
    public ResponseEntity<Object> getVenues(){
        List<Venue> venues = venueService.getAllVenues();
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }
    @GetMapping(value= "/venues/{id}")
    public ResponseEntity<Object> getVenue(@PathVariable("id") Long id) {
        Venue venue = venueService.getVenue(id);
        venue.setRating(venueService.calculateAverageRating(venue));
        return new ResponseEntity<>(venueService.getVenue(id), HttpStatus.OK);
    }

    @DeleteMapping(value="/userOwner/{username}/venues/{venueId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username, @PathVariable("venueId") Long id, Principal principal) {
        if(principal.getName().equals(username)){
            venueService.deleteUserVenueById(username, id);
            return new ResponseEntity<>("Venue deleted", HttpStatus.OK);
        }else throw new NotAuthorizedException("You are not authorized to make this request");
    }
    @PostMapping(value= "/userOwner/{username}/venues")
    public ResponseEntity<Object> addUserVenue(@PathVariable String username, @RequestBody @Validated Venue venue, Principal principal){
         if(principal.getName().equals(username)){
             venueService.addVenueToUser(venue, username);
             return new ResponseEntity<>("Venue Created",HttpStatus.CREATED);}
         else throw new NotAuthorizedException("You are not authorized to make this request");
    }
    }






