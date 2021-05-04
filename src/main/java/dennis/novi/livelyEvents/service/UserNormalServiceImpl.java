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
    public UserNormal getUser(long id){
        if (userNormalRepository.existsById(id)) {
            return userNormalRepository.findById(id).orElse(null);
        } else {
            throw new RecordNotFoundException("This id doesn't exist: " + id);
        }
    }
    @Override
    public List<UserNormal>getUserUserNameStartsWith(String userName){
        return userNormalRepository.findAllByUserNameStartingWith(userName);

    }
    @Override
    public void save(UserNormal user){
        userNormalRepository.save(user);

    }
    @Override
    public void deleteById(long id) {
        if (userNormalRepository.existsById(id)) {
            userNormalRepository.deleteById(id);}
        else {
            throw new RecordNotFoundException("The following ID can't be deleted because it doesnt exist. ID :" + id);
        }
    }
}
