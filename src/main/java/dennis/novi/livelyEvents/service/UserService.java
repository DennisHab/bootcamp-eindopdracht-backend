package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.model.Authority;
import dennis.novi.livelyEvents.model.User;
import dennis.novi.livelyEvents.model.Venue;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUser(String username);
    List<User>getUserUsernameStartsWith(String username);
    void save(User user);
    void deleteById(String username);
    Optional<User> getUserByUsername(String username);
    Set<Authority> getAuthorities (String username);
    void addAuthority(String username, String authority);
    Boolean userExists(String username);
    void updateUser(String username, User user);



}
