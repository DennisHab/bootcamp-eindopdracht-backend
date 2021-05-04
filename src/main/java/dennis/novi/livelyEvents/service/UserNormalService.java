package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.model.UserNormal;

import java.util.List;

public interface UserNormalService {
    List<UserNormal> getAllUsers();
    UserNormal getUser(long id);
    List<UserNormal>getUserUserNameStartsWith(String userName);
    void save(UserNormal user);
    void deleteById(long id) ;
}
