package com.masterproject.musigame.adapter.rest.images;

import com.masterproject.musigame.images.ImageId;
import com.masterproject.musigame.images.ImagesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin("http://www.musigame-front.azurewebsites.net")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/images", produces = {APPLICATION_JSON_VALUE})
public class ImagesController {
    private final ImagesService service;

    @GetMapping("/{imageId}")
    public ResponseEntity<Object> getImageById(@PathVariable("imageId") String imageId) {
        var image = service.findById(ImageId.of(imageId));
        if (image.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(image);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping
    public ResponseEntity<Object> getAllImages() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }
}
