package com.masterproject.musigame.adapter.rest.rooms;

import com.masterproject.musigame.rooms.Creator;
import com.masterproject.musigame.rooms.RoomId;
import com.masterproject.musigame.rooms.RoomsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/rooms", produces = {APPLICATION_JSON_VALUE})
public class RoomsController {
    private final RoomsService service;


    @GetMapping("/{roomId}")
    public ResponseEntity<Object> getRoomById(@PathVariable String roomId) {
        var room = service.findById(RoomId.build(roomId));
        if (room.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(room);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> createRoom(@RequestBody Creator creator) {
        var room = service.save(creator);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }
}
