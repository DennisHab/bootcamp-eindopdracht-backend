package dennis.novi.livelyEvents.repository;

import dennis.novi.livelyEvents.model.UserOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOwnerRepository extends JpaRepository<UserOwner, Long> {
    List<UserOwner> findAllByUserNameStartingWith(String title);
}
