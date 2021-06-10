package dennis.novi.livelyEvents.controller;

import dennis.novi.livelyEvents.model.Image;
import dennis.novi.livelyEvents.model.User;
import dennis.novi.livelyEvents.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ImageController {
    @Autowired
    private ImageRepository imageRepository;

    @GetMapping(value = "/images")
    public ResponseEntity<Object> getUsers() {
        List<Image> images = imageRepository.findAll();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
}
