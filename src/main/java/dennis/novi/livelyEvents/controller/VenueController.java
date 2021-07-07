package dennis.novi.livelyEvents.controller;
import dennis.novi.livelyEvents.exception.NotAuthorizedException;
import dennis.novi.livelyEvents.model.*;
import dennis.novi.livelyEvents.repository.UserOwnerRepository;
import dennis.novi.livelyEvents.repository.VenueRepository;
import dennis.novi.livelyEvents.service.FileUploadService;
import dennis.novi.livelyEvents.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge=3600)
public class VenueController {
    @Autowired
    private VenueService venueService;
    @Autowired
    private  FileUploadService fileUploadService;

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
    @GetMapping(value="/venues/venuename/{venueName}")
    public ResponseEntity<Object> getVenueByVenueName(@PathVariable("venueName") String venueName){
        Venue venue = venueService.getVenueByVenueName(venueName).get();
        return new ResponseEntity<>(venue, HttpStatus.OK);
    }

    @DeleteMapping(value="/userOwner/{username}/venues/{venueId}")
    public ResponseEntity<Object> deleteVenue(@PathVariable("username") String username, @PathVariable("venueId") Long id, Principal principal) {
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

    @PostMapping(value="{username}/venue/{venueId}/uploadimage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addImageToVenue(@PathVariable(value= "username") String username, @PathVariable(value = "venueId") Long id, @RequestParam("file") MultipartFile file, Principal principal ){
        if (principal.getName().equals(username)){
        venueService.uploadImageToVenue(file, id, username);
        return new ResponseEntity<>("File uploaded", HttpStatus.OK);}
        else throw new NotAuthorizedException("You are not authorized to make this request");
    }
    }






