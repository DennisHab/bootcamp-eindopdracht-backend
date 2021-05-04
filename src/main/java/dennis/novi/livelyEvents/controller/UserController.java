package dennis.novi.livelyEvents.controller;

import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.User;
import dennis.novi.livelyEvents.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping(value = "/users/userName/{userName}")
    public ResponseEntity<Object> getUsersByUserName(@PathVariable("userName") String userName) {
        List<User> users = userService.getUserUserNameStartsWith(userName);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value="/users/{id}")
    public  ResponseEntity<Object> getUser(@PathVariable("id") long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @GetMapping(value="/{id}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("id") Long userId) {
        return ResponseEntity.ok().body(userService.getAuthorities(userId));
    }


    @PostMapping(value= "/users")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>("Account Created", HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("id") Long id, @RequestBody Map<String, Object> fields, String userName) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(userName, authorityName);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new RecordNotFoundException();
        }
    }

    @DeleteMapping(value="/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return new ResponseEntity<>("Account deleted", HttpStatus.OK);
    }








}

