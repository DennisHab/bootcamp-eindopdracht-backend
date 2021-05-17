package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.model.UserNormal;

import java.util.List;

public interface UserNormalService {
    List<UserNormal> getAllUsers();
    UserNormal getUser(String username);
    List<UserNormal>getUserUsernameStartsWith(String username);
    void save(UserNormal user);
    void deleteById(String username) ;
    /*List<Double> reviewRatings(UserNormal user);*/
    Double calculateAverageRating(UserNormal user);
}
