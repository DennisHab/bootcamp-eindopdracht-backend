package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.UserOwner;
import dennis.novi.livelyEvents.repository.UserOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOwnerServiceImpl implements UserOwnerService {

    @Autowired
    private UserOwnerRepository userOwnerRepository;

    @Override
    public List<UserOwner> getAllUsers(){
        return userOwnerRepository.findAll();
    }
    @Override
    public UserOwner getUser(String username){
        if (userOwnerRepository.existsById(username)) {
            return userOwnerRepository.findById(username).orElse(null);
        } else {
            throw new RecordNotFoundException("This user doesn't exist: " + username);
        }
    }
    @Override
    public List<UserOwner>getUserUsernameStartsWith(String username){
        return userOwnerRepository.findAllByUsernameStartingWith(username);

    }
    @Override
    public void save(UserOwner user){
        userOwnerRepository.save(user);

    }
    @Override
    public void deleteById(String username) {
        if (userOwnerRepository.existsById(username)) {
            userOwnerRepository.deleteById(username);}
        else {
            throw new RecordNotFoundException("The following user can't be deleted because it doesnt exist:" + username);
        }
    }
}
