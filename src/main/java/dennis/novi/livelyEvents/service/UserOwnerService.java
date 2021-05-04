package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.model.UserOwner;

import java.util.List;

public interface UserOwnerService {
    List<UserOwner> getAllUsers();
    UserOwner getUser(long id);
    List<UserOwner>getUserUserNameStartsWith(String userName);
    void save(UserOwner user);
    void deleteById(long id) ;
}
