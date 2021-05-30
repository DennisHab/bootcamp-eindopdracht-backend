package dennis.novi.livelyEvents.service;
import dennis.novi.livelyEvents.model.Venue;
import java.util.List;
import java.util.Optional;

public interface VenueService {
    List<Venue> getAllVenues();
    Venue getVenue(Long id);
    List<Venue>getVenueVenueNameStartsWith(String VenueName);
    void save(Venue venue);
    void deleteById(Long id);
    Optional<Venue> getVenueByVenueName(String venueName);
    Long  getUserVenueId(String username);
    Double calculateAverageRating(Venue venue);
    List<Venue> findVenueByCityName(String cityName);
    void deleteUserVenueById(String username, Long id);
    Venue addVenueToUser(Venue venue, String username);
}
