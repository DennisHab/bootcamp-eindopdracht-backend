package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.exception.BadRequestException;
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
        if (userRepository.existsById(user.getUsername())) throw new UsernameTakenException("The following username already exists, please choose another one:" + user.getUsername());
        if (!user.getPassword().equals(user.getRepeatedPassword())) throw new BadRequestException("Repeated password and password don't match"); {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRepeatedPassword(passwordEncoder.encode(user.getRepeatedPassword()));
            userRepository.save(user);}
        }

    @Override
    public void updateUser(String username, User newUser){
        if (!userRepository.existsById(username)) throw new RecordNotFoundException();
        User user = userRepository.findById(username).get();
        if(!newUser.getRepeatedPassword().equals(newUser.getPassword())) throw new BadRequestException("Password doesn't match.");
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmailAddress(newUser.getEmailAddress());
        user.setDateOfBirth(newUser.getDateOfBirth());
        user.setEnabled(newUser.isEnabled());
        userRepository.save(user);
    }
    @Override
    public void updateUserPassword(String username, User newUser){
        if (!userRepository.existsById(username)){ throw new RecordNotFoundException();}else{
        User user = userRepository.findById(username).get();
        String oldPassword = user.getPassword();
        String validateOldPassword = newUser.getPasswordValidation();
        boolean match = passwordEncoder.matches(validateOldPassword, oldPassword);
        if (!match) throw new BadRequestException("Old password is incorrect");
        else if(!newUser.getRepeatedPassword().equals(newUser.getPassword())) throw new BadRequestException("Password doesn't match.");
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmailAddress(user.getEmailAddress());
        user.setDateOfBirth(user.getDateOfBirth());
        user.setEnabled(user.isEnabled());
        user.setAddress(user.getAddress());
        user.setDateOfBirth(user.getDateOfBirth());
        userRepository.save(user);}
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

}
