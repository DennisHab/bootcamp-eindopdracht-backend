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
    @GetMapping(value = "/usersNormal/userName/{userName}")
    public ResponseEntity<Object> getUsersByUserName(@PathVariable("userName") String userName) {
        List<UserNormal> users = userNormalService.getUserUserNameStartsWith(userName);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value="/usersNormal/{id}")
    public ResponseEntity getUser(@PathVariable("id") long id) {
        return new ResponseEntity<>(userNormalService.getUser(id), HttpStatus.OK);
    }

    @PostMapping(value= "/usersNormal")
    public ResponseEntity<Object> createUser(@RequestBody UserNormal user) {
        userNormalService.save(user);
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }

    @DeleteMapping(value="/usersNormal/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") long id) {
        userNormalService.deleteById(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
