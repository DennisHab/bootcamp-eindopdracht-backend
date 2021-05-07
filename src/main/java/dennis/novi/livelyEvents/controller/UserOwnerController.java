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
public class UserOwnerController {

    @Autowired
    private UserOwnerService userOwnerService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/usersOwner")
    public ResponseEntity<Object> getUsers() {
        List<UserOwner> users = userOwnerService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/usersOwner/username/{username}")
    public ResponseEntity<Object> getUsersByUserName(@PathVariable("username") String username) {
        List<UserOwner> users = userOwnerService.getUserUsernameStartsWith(username);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/usersOwner/{username}")
    public ResponseEntity<Object> getUser(@PathVariable("username") String username) {
        return new ResponseEntity<>(userOwnerService.getUser(username), HttpStatus.OK);
    }

    @PostMapping(value = "/usersOwner")
    public ResponseEntity<Object> createUser(@RequestBody UserOwner user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userOwnerService.save(user);
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/usersOwner/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userOwnerService.deleteById(username);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
