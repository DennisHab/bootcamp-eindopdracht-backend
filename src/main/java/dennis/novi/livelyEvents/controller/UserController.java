package dennis.novi.livelyEvents.controller;

import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.User;
import dennis.novi.livelyEvents.model.Venue;
import dennis.novi.livelyEvents.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<Object> getUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping(value = "/users/username/{username}")
    public ResponseEntity<Object> getUsersByUsername(@PathVariable("username") String username) {
        List<User> users = userService.getUserUsernameStartsWith(username);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping(value="/users/{username}")
    public  ResponseEntity<Object> getUser(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
    }
    @GetMapping(value="/users/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }
    @PostMapping(value= "/users")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>("Account Created", HttpStatus.CREATED);
    }
    @PostMapping(value = "users/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new RecordNotFoundException();
        }
    }
    @PutMapping(value = "users/{username}")
    public ResponseEntity<Object> updateKlant(@PathVariable("username") String username, @RequestBody User user) {
        userService.updateUser(username, user);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping(value="/users/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteById(username);
        return new ResponseEntity<>("Account deleted", HttpStatus.OK);
    }
}

