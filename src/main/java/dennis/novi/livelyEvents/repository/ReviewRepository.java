package dennis.novi.livelyEvents.repository;
import dennis.novi.livelyEvents.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByUserNormal_Username(String username);
}
