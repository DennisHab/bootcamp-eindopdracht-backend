package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.model.Event;
import dennis.novi.livelyEvents.model.UserNormal;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserNormalService {
    List<UserNormal> getAllUsers();
    UserNormal getUser(String username);
    List<UserNormal>getUserUsernameStartsWith(String username);
    ResponseEntity<Object> save(UserNormal user);
    void deleteById(String username) ;

    void addFavouriteEvent (String username, long id);
    void removeFavouriteEvent (String username, long id);
}
