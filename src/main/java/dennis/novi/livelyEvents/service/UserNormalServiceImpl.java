package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.UserNormal;
import dennis.novi.livelyEvents.repository.UserNormalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserNormalServiceImpl implements UserNormalService {

    @Autowired
    private UserNormalRepository userNormalRepository;

    @Override
    public List<UserNormal> getAllUsers(){
        return userNormalRepository.findAll();
    }
    @Override
    public UserNormal getUser(String username){
        if (userNormalRepository.existsById(username)) {
            return userNormalRepository.findById(username).orElse(null);
        } else {
            throw new RecordNotFoundException("This id doesn't exist: " + username);
        }
    }
    @Override
    public List<UserNormal>getUserUsernameStartsWith(String username){
        return userNormalRepository.findAllByUsernameStartingWith(username);

    }
    @Override
    public void save(UserNormal user){
        userNormalRepository.save(user);

    }
    @Override
    public void deleteById(String username) {
        if (userNormalRepository.existsById(username)) {
            userNormalRepository.deleteById(username);}
        else {
            throw new RecordNotFoundException("The following ID can't be deleted because it doesnt exist. ID :" + username);
        }
    }
}
