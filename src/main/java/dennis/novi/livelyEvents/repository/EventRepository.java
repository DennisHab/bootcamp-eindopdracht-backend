package dennis.novi.livelyEvents.repository;

import dennis.novi.livelyEvents.model.Event;
import dennis.novi.livelyEvents.model.User;
import dennis.novi.livelyEvents.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByNameContainingIgnoreCase(String name);
}



