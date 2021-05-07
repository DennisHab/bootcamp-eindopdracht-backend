package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.exception.UsernameTakenException;
import dennis.novi.livelyEvents.model.Authority;
import dennis.novi.livelyEvents.model.User;
import dennis.novi.livelyEvents.model.Venue;
import dennis.novi.livelyEvents.repository.UserRepository;
import dennis.novi.livelyEvents.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @Override
    public Optional<User> getUser(String username){
        return userRepository.findById(username);
    }
    @Override
    public List<User>getUserUsernameStartsWith(String username){
        return userRepository.findAllByUsernameStartingWith(username);
    }
    @Override
    public Optional<User> getUserByUsername(String username) {
        if (userRepository.existsById(username)) {
            return userRepository.findById(username);
        } else {
            throw new RecordNotFoundException("No user with this username");
        }
    }
    @Override
    public void save(User user){
        if (!userRepository.existsById(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);}
        else {
            throw new UsernameTakenException("The following username already exists, please choose another one:" + user.getUsername());
        }

    }
    @Override
    public void updateUser(String username, User newUser){
        if (!userRepository.existsById(username)) throw new RecordNotFoundException();
        User user = userRepository.findById(username).get();
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmailAddress(newUser.getEmailAddress());
        user.setDateOfBirth(newUser.getDateOfBirth());
        user.setEnabled(newUser.isEnabled());
        userRepository.save(user);
    }
    @Override
    public Boolean userExists(String username) {
        return userRepository.existsById(username);
    }
    @Override
    public void deleteById(String username) {
        if (userRepository.existsById(username)) {
        userRepository.deleteById(username);}
        else {
            throw new RecordNotFoundException("The following user can't be deleted because it doesnt exist. username :" + username);
        }
    }
    @Override
    public Set<Authority> getAuthorities(String username) {
        if(userRepository.existsById(username)) {
            User user = userRepository.findById(username).get();
            return user.getAuthorities();
        } else {
            throw new RecordNotFoundException("This user doesn't exist.");
        }
    }
    @Override
    public void addAuthority(String username, String authority) {
        if(userRepository.existsById(username)) {
            User user = userRepository.findById(username).get();
            user.addAuthority(new Authority(username, authority));
            userRepository.save(user);
        } else {
            throw new RecordNotFoundException();
        }
    }
}
