package dennis.novi.livelyEvents.service;
import dennis.novi.livelyEvents.model.Venue;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface VenueService {
    List<Venue> getAllVenues();
    Optional<Venue> getVenue(Long id);
    List<Venue>getVenueVenueNameStartsWith(String VenueName);
    void save(Venue venue);
    void deleteById(Long id);
    Optional<Venue> getVenueByVenueName(String venueName);

}
