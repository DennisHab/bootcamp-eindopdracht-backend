package dennis.novi.livelyEvents.controller;
import dennis.novi.livelyEvents.exception.NotAuthorizedException;
import dennis.novi.livelyEvents.model.UserNormal;
import dennis.novi.livelyEvents.service.UserNormalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge=3600)
public class UserNormalController {

    @Autowired
    private UserNormalService userNormalService;

    @GetMapping(value = "users/usersNormal")
    public ResponseEntity<Object> getUsers() {
        List<UserNormal> users = userNormalService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping(value="users/usersNormal/{username}")
    public ResponseEntity getUser(@PathVariable("username") String username) {
        UserNormal user = userNormalService.getUser(username);
        user.setRating(userNormalService.calculateAverageRating(user));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping(value= "/userNormal/{username}/{eventId}")
    public ResponseEntity<Object> addFavouriteEvent(@PathVariable("username") String username, @PathVariable("eventId") long id, Principal principal){
        if(principal.getName().equals(username)){
        userNormalService.addFavouriteEvent(username, id);
        return  new ResponseEntity<>("Event added to favourite events", HttpStatus.CREATED);}
        else throw new NotAuthorizedException("You are not authorized to make this request");
    }
    @DeleteMapping(value="/userNormal/{username}/{eventId}")
        public ResponseEntity<Object> removeFavouriteEvent(@PathVariable("username") String username, @PathVariable("eventId") long id, Principal principal){
        if(principal.getName().equals(username)){
            userNormalService.removeFavouriteEvent(username, id);
            return  new ResponseEntity<>("Event removed from favourite events", HttpStatus.ACCEPTED);}
        else throw new NotAuthorizedException("You are not authorized to make this request.");
    }
    @PostMapping(value= "adduser/usersNormal")
    public ResponseEntity<Object> createUser(@RequestBody UserNormal user) {
        userNormalService.save(user);
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }

}
