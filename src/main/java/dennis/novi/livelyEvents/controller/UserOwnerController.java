package dennis.novi.livelyEvents.controller;

import dennis.novi.livelyEvents.model.UserOwner;
import dennis.novi.livelyEvents.service.UserOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserOwnerController {

    @Autowired
    private UserOwnerService userOwnerService;

    @GetMapping(value = "/usersOwner")
    public ResponseEntity<Object> getUsers() {
        List<UserOwner> users = userOwnerService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/usersOwner/userName/{userName}")
    public ResponseEntity<Object> getUsersByUserName(@PathVariable("userName") String userName) {
        List<UserOwner> users = userOwnerService.getUserUserNameStartsWith(userName);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/usersOwner/{id}")
    public ResponseEntity<Object> getUser(@PathVariable("id") long id) {
        return new ResponseEntity<>(userOwnerService.getUser(id), HttpStatus.OK);
    }

    @PostMapping(value = "/usersOwner")
    public ResponseEntity<Object> createUser(@RequestBody UserOwner user) {
        userOwnerService.save(user);
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/usersOwner/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") long id) {
        userOwnerService.deleteById(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
