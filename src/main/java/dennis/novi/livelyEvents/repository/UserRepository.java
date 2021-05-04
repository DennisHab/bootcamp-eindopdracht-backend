package dennis.novi.livelyEvents.repository;
import dennis.novi.livelyEvents.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByUserNameStartingWith(String title);
    User findUserById(Long id);
    Boolean existsByUserName (String userName);
    Optional<User> findUserByUserName (String userName);
}
