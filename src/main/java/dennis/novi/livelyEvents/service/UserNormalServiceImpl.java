package dennis.novi.livelyEvents.service;
import dennis.novi.livelyEvents.exception.BadRequestException;
import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.exception.UsernameTakenException;
import dennis.novi.livelyEvents.model.Review;
import dennis.novi.livelyEvents.model.UserNormal;
import dennis.novi.livelyEvents.repository.UserNormalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserNormalServiceImpl implements UserNormalService {

    @Autowired
    private UserNormalRepository userNormalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserNormal> getAllUsers(){
        List<UserNormal> users = userNormalRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            UserNormal user = users.get(i);
            user.setRating(calculateAverageRating(user));
        }
        return users;
    }
    @Override
    public UserNormal getUser(String username){
        if (userNormalRepository.existsById(username)) {
            UserNormal user = userNormalRepository.findById(username).get();
            user.setRating(calculateAverageRating(user));
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
        if (userNormalRepository.existsById(user.getUsername())) throw new UsernameTakenException("The following username already exists, please choose another one:" + user.getUsername());
        if (!user.getPassword().equals(user.getRepeatedPassword())) throw new BadRequestException("Repeated password and password don't match"); {
            user.setRating(calculateAverageRating(user));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRepeatedPassword(passwordEncoder.encode(user.getRepeatedPassword()));
            userNormalRepository.save(user);}
        }

    @Override
    public void deleteById(String username) {
        if (userNormalRepository.existsById(username)) {
            userNormalRepository.deleteById(username);}
        else {
            throw new RecordNotFoundException("The following ID can't be deleted because it doesnt exist. ID :" + username);
        }
    }
    /*Method to calculate average rating of the reviews given by user which indicates the thrustworthiness of reviews of this user. If no reviews are present it will default to 6.0*/
    @Override
    public Double calculateAverageRating(UserNormal user) {
        List<Double> userReviewRatings = new ArrayList<>();
        if (user.getReviews() == null){
            userReviewRatings.add(6.0);
        } else {List<Review> userReviews = user.getReviews();
            for (Review review : userReviews) {
                userReviewRatings.add(review.getReviewRating());
            }
        }
        double totalReviewRating = 0.0;
        for (Double userReviewRating : userReviewRatings) {
            totalReviewRating = totalReviewRating + userReviewRating;
        }
        if (user.getReviews() == null) {
            return 6.0; } else {
        List<Review> userTotalReviews = user.getReviews();
        int totalReviews = userTotalReviews.size();
            return Math.round((totalReviewRating/totalReviews) * 10.0) / 10.0;
        }
    }
}


