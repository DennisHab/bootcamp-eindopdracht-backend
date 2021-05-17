package dennis.novi.livelyEvents.service;

import dennis.novi.livelyEvents.exception.RecordNotFoundException;
import dennis.novi.livelyEvents.model.UserOwner;
import dennis.novi.livelyEvents.model.Venue;
import dennis.novi.livelyEvents.repository.UserOwnerRepository;
import dennis.novi.livelyEvents.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    VenueRepository venueRepository;
    @Autowired
    UserOwnerRepository userOwnerRepository;

    @Override
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }
    @Override
    public Optional<Venue> getVenue(Long id) {
        return venueRepository.findById(id);
    }
    @Override
    public List<Venue> getVenueVenueNameStartsWith(String venueName){
        return venueRepository.findAllByVenueNameStartingWith(venueName);
    }
    @Override
    public void save(Venue venue) {
        venueRepository.save(venue);
    }
    @Override
    public void deleteById(Long id) {
        venueRepository.deleteById(id);
    }
    @Override
    public Optional<Venue> getVenueByVenueName(String venueName) {
        if (venueRepository.existsVenueByVenueName(venueName)) {
            return venueRepository.findByVenueName(venueName);
        } else {
            throw new RecordNotFoundException("This venue doesn't exist");
        }
    }
    @Override
    public Long getUserVenueId(String username) {
    UserOwner user = userOwnerRepository.findById(username).get();
    List<Venue> venues = user.getVenueList();
    long venueId = 0;
    for (int i = 0; i < venues.size(); i++) {
        Venue venue = venues.get(i);
        venueId = venue.getId();

    } return venueId;}

}
