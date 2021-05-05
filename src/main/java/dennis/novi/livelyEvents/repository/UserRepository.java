package dennis.novi.livelyEvents.repository;
import dennis.novi.livelyEvents.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAllByUsernameStartingWith(String title);


    /*Optional<User> findById(String userName);*/
    /*Boolean existsById (String userName);
    Optional<User> findUserByUserName (String userName);
    Void deleteById (String userName);*/
}
