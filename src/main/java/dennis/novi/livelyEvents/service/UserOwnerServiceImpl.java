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
    public UserOwner getUser(long id){
        if (userOwnerRepository.existsById(id)) {
            return userOwnerRepository.findById(id).orElse(null);
        } else {
            throw new RecordNotFoundException("This id doesn't exist: " + id);
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
    public void deleteById(long id) {
        if (userOwnerRepository.existsById(id)) {
            userOwnerRepository.deleteById(id);}
        else {
            throw new RecordNotFoundException("The following ID can't be deleted because it doesnt exist. ID :" + id);
        }
    }
}
