package dennis.novi.livelyEvents.service;
import dennis.novi.livelyEvents.exception.FileStorageException;
import dennis.novi.livelyEvents.exception.NotAuthorizedException;
import dennis.novi.livelyEvents.model.Event;
import dennis.novi.livelyEvents.model.UserOwner;
import dennis.novi.livelyEvents.model.Venue;
import dennis.novi.livelyEvents.repository.EventRepository;
import dennis.novi.livelyEvents.repository.UserOwnerRepository;
import dennis.novi.livelyEvents.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileUploadService {

    @Value("${app.upload.dir:${C:\\Users\\DennisHabets\\WebstormProjects\\novi-eindopdracht-lively\\public}}")
    public String uploadDir;
    @Autowired
    VenueRepository venueRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserOwnerRepository userOwnerRepository;

    public void uploadImageToVenue(MultipartFile file, Long id, String username) {
        UserOwner userOwner = userOwnerRepository.findById(username).get();
        List<Venue> userVenues = userOwner.getVenueList();
        List<Long> userVenueIds = new ArrayList<>();
        userVenues.forEach(venue -> userVenueIds.add(venue.getId()));
        if(userVenueIds.contains(id)){
        try {
                Path copyLocation = Paths
                        .get(uploadDir +  File.separator + StringUtils.cleanPath("venue" + id + file.getOriginalFilename()));
                Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
                Venue venue = venueRepository.findById(id).get();
                venue.setImage("venue" + id + file.getOriginalFilename());
                venueRepository.save(venue);

        } catch (Exception e) {
                e.printStackTrace();
                throw new FileStorageException("Could not store file " + file.getOriginalFilename()
                        + ". Please try again!");
        }} else throw new NotAuthorizedException("This venue is not yours");
    }

    public void uploadImageToEvent(MultipartFile file, Long id, String username) {
        UserOwner userOwner = userOwnerRepository.findById(username).get();
        List<Venue> userVenues = userOwner.getVenueList();
        List<Event> userVenueEvents = new ArrayList<>();
        userVenues.forEach(venue -> userVenueEvents.addAll(venue.getEvents()));
        List<Long> userEventIds = new ArrayList<>();
        userVenueEvents.forEach(event -> userEventIds.add(event.getId()));
        if(userEventIds.contains(id)){
        try {
                Path copyLocation = Paths
                        .get(uploadDir +  File.separator + StringUtils.cleanPath("event" + id + file.getOriginalFilename()));
                Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
                Event event = eventRepository.findById(id).get();
                event.setImage("event" + id + file.getOriginalFilename());
                eventRepository.save(event);

        } catch (Exception e) {
                e.printStackTrace();
                throw new FileStorageException("Could not store file " + file.getOriginalFilename()
                        + ". Please try again!");
        }} else throw new NotAuthorizedException("This event is not yours.");
    }
}
