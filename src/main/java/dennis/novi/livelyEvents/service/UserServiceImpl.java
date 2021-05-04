package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.Authority;
import dennis.novi.livelyEvents.model.User;
import dennis.novi.livelyEvents.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @Override
    public User getUser(long id){
        if (userRepository.existsById(id)) {
        return userRepository.findById(id).orElse(null);
    } else {
       throw new RecordNotFoundException("This id doesn't exist: " + id);
        }
    }
    @Override
    public List<User>getUserUserNameStartsWith(String userName){
        return userRepository.findAllByUserNameStartingWith(userName);
    }
    @Override
    public Optional<User> getUserByUsername(String userName) {
        if (userRepository.existsByUserName(userName)) {
            return userRepository.findUserByUserName(userName);
        } else {
            throw new RecordNotFoundException("No user with this username");
        }
    }
    @Override
    public void save(User user){
        userRepository.save(user);

    }
    @Override
    public void deleteById(long id) {
        if (userRepository.existsById(id)) {
        userRepository.deleteById(id);}
        else {
            throw new RecordNotFoundException("The following user can't be deleted because it doesnt exist. User ID :" + id);
        }
    }
    @Override
    public Set<Authority> getAuthorities(long id) {
        if(userRepository.existsById(id)) {
            User user = userRepository.findUserById(id);
            return user.getAuthorities();
        } else {
            throw new RecordNotFoundException("This user doesn't exist.");
        }
    }
    @Override
    public void addAuthority(String userName, String authority) {
        if(userRepository.existsByUserName(userName)) {
            User user = userRepository.findUserByUserName(userName).get();
            user.addAuthority(new Authority(userName, authority));
            userRepository.save(user);
        } else {
            throw new RecordNotFoundException();
        }
    }


}
