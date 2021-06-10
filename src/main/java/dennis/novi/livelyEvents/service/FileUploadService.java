package dennis.novi.livelyEvents.service;
import dennis.novi.livelyEvents.exception.FileStorageException;
import dennis.novi.livelyEvents.model.Event;
import dennis.novi.livelyEvents.model.Venue;
import dennis.novi.livelyEvents.repository.EventRepository;
import dennis.novi.livelyEvents.repository.ImageRepository;
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

@Service
public class FileUploadService {

    @Value("${app.upload.dir:${C:\\Users\\DennisHabets\\WebstormProjects\\novi-eindopdracht-lively\\public}}")
    public String uploadDir;
    @Autowired
    VenueRepository venueRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    EventRepository eventRepository;


    public void uploadImageToVenue(MultipartFile file, Long id) {
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
        }
    }

    public void uploadImageToEvent(MultipartFile file, Long id) {
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
        }
    }
}
