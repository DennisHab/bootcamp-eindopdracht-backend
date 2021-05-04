package dennis.novi.livelyEvents.repository;

import dennis.novi.livelyEvents.model.UserNormal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserNormalRepository extends JpaRepository<UserNormal, Long> {
    List<UserNormal> findAllByUserNameStartingWith(String title);
}
