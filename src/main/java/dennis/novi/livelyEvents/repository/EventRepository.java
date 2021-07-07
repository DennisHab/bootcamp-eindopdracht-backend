package dennis.novi.livelyEvents.repository;
import dennis.novi.livelyEvents.model.Event;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByNameContainingIgnoreCase(String name);

    @NotNull Page<Event> findAll(@NotNull Pageable pageable);
}



