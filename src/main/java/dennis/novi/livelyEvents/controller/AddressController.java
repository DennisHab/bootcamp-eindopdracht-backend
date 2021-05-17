package dennis.novi.livelyEvents.controller;
import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.User;
import dennis.novi.livelyEvents.model.Address;
import dennis.novi.livelyEvents.model.UserOwner;
import dennis.novi.livelyEvents.model.Venue;
import dennis.novi.livelyEvents.repository.UserOwnerRepository;
import dennis.novi.livelyEvents.repository.UserRepository;
import dennis.novi.livelyEvents.repository.AddressRepository;
import dennis.novi.livelyEvents.repository.VenueRepository;
import dennis.novi.livelyEvents.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
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
    @GetMapping(value = "/addresses/city/{city}")
    public ResponseEntity<Object> getAddressesByCity(@PathVariable("city") String city) {
        List<Address> addresses = addressService.getAddressCityStartsWith(city);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping(value="/addresses/{id}")
    public  ResponseEntity<Object> getAddress(@PathVariable("id") long id) {
        return new ResponseEntity<>(addressService.getAddress(id), HttpStatus.OK);
    }

    @PostMapping(value= "/addresses")
    public ResponseEntity<Object> createAddress(@RequestBody Address address) {
        addressService.save(address);
        return new ResponseEntity<>("Address Created", HttpStatus.CREATED);
    }

    @GetMapping(value="/users/{username}/addresses/{id}")
    public ResponseEntity<Object> getUserAddress(@PathVariable("username") String username, @PathVariable("id") long id) {
        return new ResponseEntity<>(addressRepository.findById(id), HttpStatus.OK);
    }
    @PostMapping(value= "/users/{username}/addresses")
    public ResponseEntity<Object> addUserAddress(@PathVariable("username") String username, @RequestBody @Validated Address address) {
       User user = userRepository.findById(username).get();
       if(user.getAddress() == null && user.getUsername() == username && address.getUser() == null) {
       address.setUser(user);
       addressService.save(address);
       return new ResponseEntity<>("Address Created",HttpStatus.CREATED);}
        else {
            throw new RecordNotFoundException("Either the ID requested doesn't exist or this user already has an address");
       }
    }
    @DeleteMapping(value="/addresses/{id}")
    public ResponseEntity<Object> deleteAddress(@PathVariable("id") long id) {
        addressService.deleteById(id);
        return new ResponseEntity<>("Address deleted", HttpStatus.OK);
    }

    @PostMapping(value= "/venues/{id}/address")
    public ResponseEntity<Object> addVenueAddress(@PathVariable Long id, @RequestBody @Validated Address address) {
        Venue venue = venueRepository.findById(id).get();
        if(venue.getAddress() == null && venue.getId() == id && address.getUser() == null) {
            address.setVenue(venue);
            venue.setAddress(address);
            addressService.save(address);
            return new ResponseEntity<>("Address Created",HttpStatus.CREATED);}
        else {
            throw new RecordNotFoundException("Either the ID requested doesn't exist or this venue already has an address");
        }
    }
    @PostMapping(value= "/usersOwner/{username}/venues/{id}/address")
    public ResponseEntity<Object> addVenueAddressToUser(@PathVariable Long id, @PathVariable String username, @RequestBody @Validated Address address) {
        Venue venue = venueRepository.findById(id).get();
        UserOwner user= userOwnerRepository.findById(username).get();
        if(venue.getAddress() == null && venue.getId() == id && address.getVenue() == null) {
            address.setVenue(venue);
            venue.setAddress(address);
            addressService.save(address);
            return new ResponseEntity<>("Address Created",HttpStatus.CREATED);}
        else {
            throw new RecordNotFoundException("Either the ID requested doesn't exist or this venue already has an address");
        }
    }
    @PutMapping(value="/users/{username}/addresses/{id}")
    public ResponseEntity<Object> updateUserAddress(@PathVariable("username") String username, @PathVariable("id") Long id ,@RequestBody Address address){
        addressService.updateUserAddress(address, id, username);
        return ResponseEntity.noContent().build();
    }

}
