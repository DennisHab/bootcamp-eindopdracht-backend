package dennis.novi.livelyEvents.controller;
import dennis.novi.livelyEvents.exception.NotAuthorizedException;
import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.filter.JwtRequestFilter;
import dennis.novi.livelyEvents.model.User;
import dennis.novi.livelyEvents.model.Address;
import dennis.novi.livelyEvents.model.UserOwner;
import dennis.novi.livelyEvents.model.Venue;
import dennis.novi.livelyEvents.repository.UserOwnerRepository;
import dennis.novi.livelyEvents.repository.UserRepository;
import dennis.novi.livelyEvents.repository.AddressRepository;
import dennis.novi.livelyEvents.repository.VenueRepository;
import dennis.novi.livelyEvents.service.AddressService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge=3600)
public class AddressController {

    @Autowired
    private AddressService addressService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private UserOwnerRepository userOwnerRepository;


    @GetMapping(value = "/addresses")
    public ResponseEntity<Object> getAddresses() {
        List<Address> addresses = addressService.getAllAddresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }
    @GetMapping(value="/addresses/{id}")
    public  ResponseEntity<Object> getAddress(@PathVariable("id") long id) {
        return new ResponseEntity<>(addressService.getAddress(id), HttpStatus.OK);   }

    @GetMapping(value="/users/{username}/addresses/{id}")
    public ResponseEntity<Object> getUserAddress(@PathVariable("username") String username, @PathVariable("id") long id) {
        return new ResponseEntity<>(addressRepository.findById(id), HttpStatus.OK);}

    @PostMapping(value= "/user/{username}/addresses")
    public ResponseEntity<Object> addUserAddress(@PathVariable("username") String username, @RequestBody @Validated Address address, Principal principal) {
        if(principal.getName().equals(username)){
                User user = userRepository.findById(username).get();
            if(user.getAddress() == null && user.getUsername() == username && address.getUser() == null) {
                address.setUser(user);
                addressService.save(address);
                return new ResponseEntity<>("Address Created",HttpStatus.CREATED);}
            else {
                throw new RecordNotFoundException("Either the ID requested doesn't exist or this user already has an address");}}
            else throw new NotAuthorizedException("You are not authorized to make this request");
       }

    @PutMapping(value="/user/{username}/addresses/{id}")
    public ResponseEntity<Object> updateUserAddress(@PathVariable("username") String username, @PathVariable("id") Long id ,
                                                    @RequestBody Address address, Principal principal){
        if (principal.getName().equals(username)){
            addressService.updateUserAddress(address, id, username);
            return ResponseEntity.noContent().build();}
        else throw new NotAuthorizedException("You are not authorized to make this request");
    }



}
