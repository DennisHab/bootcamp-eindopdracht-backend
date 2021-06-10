package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.exception.BadRequestException;
import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.exception.UsernameTakenException;
import dennis.novi.livelyEvents.model.Authority;
import dennis.novi.livelyEvents.model.UserOwner;
import dennis.novi.livelyEvents.repository.UserOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOwnerServiceImpl implements UserOwnerService {

    @Autowired
    private UserOwnerRepository userOwnerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        if (userOwnerRepository.existsById(user.getUsername())) throw new UsernameTakenException("The following username already exists, please choose another one:" + user.getUsername());
        if (user.getPassword().equals(user.getRepeatedPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRepeatedPassword(passwordEncoder.encode(user.getRepeatedPassword()));
            Authority authority = new Authority();
            authority.setUsername(user.getUsername());
            authority.setAuthority("ROLE_USERSOWNER");
            user.addAuthority(authority);
            userOwnerRepository.save(user);} else {
            throw new BadRequestException("Repeated password and password don't match");
        }
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
