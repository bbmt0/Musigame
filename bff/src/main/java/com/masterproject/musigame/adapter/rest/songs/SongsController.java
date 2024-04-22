package com.masterproject.musigame.adapter.rest.songs;

import com.masterproject.musigame.rooms.RoomId;
import com.masterproject.musigame.songs.SongsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/songs", produces = {APPLICATION_JSON_VALUE})
public class SongsController {
    private final SongsService service;

    @GetMapping("/{keyword}")
    public ResponseEntity<Object> getRoomById(@PathVariable String keyword) {
        var songs = service.findByKeyword(keyword);
        if (songs.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(songs);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
