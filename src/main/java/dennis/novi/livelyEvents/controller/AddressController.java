package dennis.novi.livelyEvents.controller;
import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.User;
import dennis.novi.livelyEvents.model.Address;
import dennis.novi.livelyEvents.repository.UserRepository;
import dennis.novi.livelyEvents.repository.AddressRepository;
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
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

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

    @GetMapping(value="/users/{userId}/addresses")
    public ResponseEntity<Object> getUserAddress(@PathVariable Long userId) {
        return new ResponseEntity<>(addressRepository.findByUserId(userId), HttpStatus.OK);
    }
    @PostMapping(value= "/users/{userId}/addresses")
    public ResponseEntity<Object> addUserAddress(@PathVariable Long userId, @RequestBody @Validated Address address) {
       User user = userRepository.findUserById(userId);
       if(user.getAddress() == null && user.getId() == userId && address.getUser() == null) {
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

}
