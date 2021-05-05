package dennis.novi.livelyEvents.controller;

import dennis.novi.livelyEvents.model.UserNormal;
import dennis.novi.livelyEvents.service.UserNormalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserNormalController {

    @Autowired
    private UserNormalService userNormalService;

    @GetMapping(value = "/usersNormal")
    public ResponseEntity<Object> getUsers() {
        List<UserNormal> users = userNormalService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping(value = "/usersNormal/username/{username}")
    public ResponseEntity<Object> getUsersByUsername(@PathVariable("username") String username) {
        List<UserNormal> users = userNormalService.getUserUsernameStartsWith(username);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value="/usersNormal/{username}")
    public ResponseEntity getUser(@PathVariable("username") String username) {
        return new ResponseEntity<>(userNormalService.getUser(username), HttpStatus.OK);
    }

    @PostMapping(value= "/usersNormal")
    public ResponseEntity<Object> createUser(@RequestBody UserNormal user) {
        userNormalService.save(user);
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }

    @DeleteMapping(value="/usersNormal/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") String username) {
        userNormalService.deleteById(username);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
