package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.model.Authority;
import dennis.novi.livelyEvents.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface UserService {
    List<User> getAllUsers();
    User getUser(long id);
    List<User>getUserUserNameStartsWith(String userName);
    void save(User user);
    void deleteById(long id);
    Optional<User> getUserByUsername(String userName);
    Set<Authority> getAuthorities (long id);
    void addAuthority(String userName, String authority);


}
