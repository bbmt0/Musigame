package com.masterproject.musigame.adapter.rest.artists;

import com.masterproject.musigame.artists.ArtistsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = {"http://localhost:3000", "https://musigame-front.azurewebsites.net"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/artists", produces = {APPLICATION_JSON_VALUE})
public class ArtistsController {
    private final ArtistsService service;

    @GetMapping("/{keyword}")
    public ResponseEntity<Object> getRoomById(@PathVariable("keyword") String keyword) {
        var artists = service.findByKeyword(keyword);
        if (artists.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(artists);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
