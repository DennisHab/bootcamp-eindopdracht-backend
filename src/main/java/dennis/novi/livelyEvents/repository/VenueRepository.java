package dennis.novi.livelyEvents.repository;

import dennis.novi.livelyEvents.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VenueRepository extends JpaRepository<Venue, Long> {
    List<Venue> findAllByVenueNameStartingWith(String venueName);
    Optional<Venue> findByVenueName(String venueName);
    Boolean existsVenueByVenueName(String venueName);
}
