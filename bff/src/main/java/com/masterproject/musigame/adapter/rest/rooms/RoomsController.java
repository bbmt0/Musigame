package com.masterproject.musigame.adapter.rest.rooms;

import com.masterproject.musigame.rooms.Creator;
import com.masterproject.musigame.rooms.GameType;
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

    @PutMapping("/{roomId}/start")
    public ResponseEntity<Object> startRoom(@PathVariable String roomId, @RequestParam String creatorId, @RequestParam GameType gameType) {
        var room = service.findById(RoomId.build(roomId));
        if (room.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var creator = room.get().getCreator();
        if (!creator.getPlayerId().equals(creatorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var updatedRoom = service.startGame(room.get(), creator, gameType);
        if (updatedRoom.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedRoom);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> createRoom(@RequestBody Creator creator) {
        var room = service.save(creator);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }
}
