package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.model.UserOwner;

import java.util.List;

public interface UserOwnerService {
    List<UserOwner> getAllUsers();
    UserOwner getUser(String username);
    List<UserOwner>getUserUsernameStartsWith(String username);
    void save(UserOwner user);
    void deleteById(String username) ;
}
