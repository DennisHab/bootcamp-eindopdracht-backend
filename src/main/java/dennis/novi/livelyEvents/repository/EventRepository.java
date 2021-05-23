package dennis.novi.livelyEvents.repository;

import dennis.novi.livelyEvents.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
