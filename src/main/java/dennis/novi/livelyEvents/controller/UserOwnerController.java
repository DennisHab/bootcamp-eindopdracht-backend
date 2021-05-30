package dennis.novi.livelyEvents.controller;

import dennis.novi.livelyEvents.model.UserOwner;
import dennis.novi.livelyEvents.service.UserOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge=3600)
public class UserOwnerController {

    @Autowired
    private UserOwnerService userOwnerService;

    @GetMapping(value = "users/usersOwner")
    public ResponseEntity<Object> getUsers() {
        List<UserOwner> users = userOwnerService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping(value = "users/usersOwner/{username}")
    public ResponseEntity<Object> getUser(@PathVariable("username") String username) {
        return new ResponseEntity<>(userOwnerService.getUser(username), HttpStatus.OK);
    }
    @PostMapping(value = "adduser/usersOwner")
    public ResponseEntity<Object> createUser(@RequestBody UserOwner user) {
        userOwnerService.save(user);
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }
}
