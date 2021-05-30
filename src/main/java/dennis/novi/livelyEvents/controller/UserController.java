package dennis.novi.livelyEvents.controller;

import dennis.novi.livelyEvents.exception.NotAuthorizedException;
import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.User;
import dennis.novi.livelyEvents.model.Venue;
import dennis.novi.livelyEvents.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge=3600)

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<Object> getUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping(value="/user/{username}")
    public  ResponseEntity<Object> getUser(@PathVariable("username") String username, Principal principal) {
        if(principal.getName().equals(username)){
        return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);}
        else throw new NotAuthorizedException("You are not authorized to make this request");
    }
    @GetMapping(value="/authorities/{username}")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }
    @PostMapping(value = "authorities/{username}")
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
    @PutMapping(value = "updateUser/{username}")
    public ResponseEntity<Object> updateUser(@PathVariable("username") String username, @RequestBody User user, Principal principal) {
        if(principal.getName().equals(username)){
        userService.updateUser(username, user);
        return ResponseEntity.noContent().build();}
        else throw new NotAuthorizedException("You are not authorized to make this request");
    }
    @PutMapping(value = "/changepassword/{username}")
    public ResponseEntity<Object> updateUserPassword(@PathVariable("username") String username, @RequestBody User user, Principal principal) {
        if(principal.getName().equals(username)){
            userService.updateUserPassword(username, user);
            return ResponseEntity.noContent().build();}
        else throw new NotAuthorizedException("You are not authorized to make this request");
    }
    @DeleteMapping(value="/deleteUser/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username, Principal principal) {
        if(principal.getName().equals(username)){
            userService.deleteById(username);
            return new ResponseEntity<>("Account deleted", HttpStatus.OK);}
        else throw new NotAuthorizedException("You are not authorized to make this request");
    }
}

