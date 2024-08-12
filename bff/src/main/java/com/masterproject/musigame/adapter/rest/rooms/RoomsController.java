package com.masterproject.musigame.adapter.rest.rooms;

import com.masterproject.musigame.rooms.*;
import com.masterproject.musigame.songs.Song;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = {"http://localhost:3000", "https://musigame-front.azurewebsites.net"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/rooms", produces = {APPLICATION_JSON_VALUE})
public class RoomsController {
    private final RoomsService service;
    private static final String ROOM_NOT_FOUND = "Room not found";
    private static final String PLAYER_NOT_FOUND = "Player not found";
    private static final String NOT_CREATOR = "Player is not the creator";
    private static final String NOT_CURRENT_BOSS = "Player is not the current boss";
    private static final String CURRENT_BOSS = "Player is the current boss";
    private static final String GAME_ALREADY_STARTED = "Game already started";
    private static final String PLAYER_ALREADY_IN_ROOM = "Player already in room";
    private static final String ROOM_FULL = "Room is full";
    private static final String ROUND_IS_NOT_CURRENT = "Round is not current";
    private static final String WINNING_SONG_ALREADY_SELECTED = "Winning song already selected";
    private static final String GAME_ALREADY_FINISHED = "Game already finished";


    @GetMapping("/{roomId}")
    public ResponseEntity<Object> getRoomById(@PathVariable("roomId") String roomId) {
        System.out.println("RoomsController.getRoomById");
        var room = retrieveRoom(roomId);
        if (room.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(room);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{roomId}/start")
    public ResponseEntity<Object> startRoom(@PathVariable("roomId") String roomId, @RequestParam("creatorId") String creatorId, @RequestParam("gameType") GameType gameType, @RequestParam("numberOfRounds") Integer numberOfRounds) {
        System.out.println("RoomsController.startRoom");
        var room = retrieveRoom(roomId);
        if (room.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ROOM_NOT_FOUND);
        }
        var creator = room.get().getCreator();
        if (!creator.getPlayerId().equals(creatorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(NOT_CREATOR);
        }
        var updatedRoom = service.startGame(room.get(), gameType, numberOfRounds);
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

    @PutMapping("/{roomId}/submit-sentence")
    public ResponseEntity<Object> submitSentence(@PathVariable("roomId") String roomId, @RequestParam("currentBossId") String currentBossId, @RequestParam("roundId") Integer roundId, @RequestParam("sentence") String sentence) {
        var room = retrieveRoom(roomId);
        if (room.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ROOM_NOT_FOUND);
        }
        if (room.get().getRounds().get(roundId - 1).getCurrentBoss() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ROUND_IS_NOT_CURRENT);
        }
        var currentBoss = room.get().getRounds().get(roundId - 1).getCurrentBoss();
        if (!currentBoss.getPlayerId().equals(currentBossId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(NOT_CURRENT_BOSS);
        }
        var updatedRoom = service.submitSentence(room.get(), roundId, sentence);
        if (updatedRoom.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedRoom);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{roomId}/submit-song")
    public ResponseEntity<Object> submitSong(@PathVariable("roomId") String roomId, @RequestParam("playerId") String playerId, @RequestParam("roundId") Integer roundId, @RequestBody Song song) {
        var room = retrieveRoom(roomId);
        if (room.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ROOM_NOT_FOUND);
        }
        if (room.get().getRounds().get(roundId - 1).getCurrentBoss() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ROUND_IS_NOT_CURRENT);
        }
        var currentBoss = room.get().getRounds().get(roundId - 1).getCurrentBoss();
        if (currentBoss.getPlayerId().equals(playerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(CURRENT_BOSS);
        }
        var updatedRoom = service.submitSong(room.get(), roundId, playerId, song);
        if (updatedRoom.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedRoom);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{roomId}/join")
    public ResponseEntity<Object> joinRoom(@PathVariable("roomId") String roomId, @RequestBody Player player) {
        var room = retrieveRoom(roomId);
        if (room.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ROOM_NOT_FOUND);
        }
        if (room.get().getPlayers().stream().anyMatch(p -> p.getPlayerId().equals(player.getPlayerId()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PLAYER_ALREADY_IN_ROOM);
        }
        if (room.get().getGame().isGameLaunched()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GAME_ALREADY_STARTED);
        }
        if (room.get().getPlayers().size() >= 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ROOM_FULL);
        }
        var updatedRoom = service.join(room.get(), player);
        if (updatedRoom.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedRoom);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{roomId}/leave")
    public ResponseEntity<Object> leaveRoom(@PathVariable("roomId") String roomId, @RequestParam("playerId") String playerId) {
        var room = retrieveRoom(roomId);
        if (room.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ROOM_NOT_FOUND);
        }
        var player = room.get().getPlayers().stream()
                .filter(p -> p.getPlayerId().equals(playerId))
                .findFirst()
                .orElse(null);
        if (player == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PLAYER_NOT_FOUND);
        }
        var updatedRoom = service.leave(room.get(), player);
        if (updatedRoom.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedRoom);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PutMapping("/{roomId}/select-song")
    public ResponseEntity<Object> selectSong(@PathVariable("roomId") String roomId, @RequestParam("currentBossId") String currentBossId, @RequestParam("playerId") String playerId, @RequestParam("roundId") Integer roundId) {
        var room = retrieveRoom(roomId);
        if (room.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ROOM_NOT_FOUND);
        }
        if (room.get().getRounds().get(roundId - 1).getCurrentBoss() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ROUND_IS_NOT_CURRENT);
        }
        var currentBoss = room.get().getRounds().get(roundId - 1).getCurrentBoss();
        if (!currentBoss.getPlayerId().equals(currentBossId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(NOT_CURRENT_BOSS);
        }
        if (room.get().getRounds().get(roundId - 1).getWinningSong() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(WINNING_SONG_ALREADY_SELECTED);
        }
        var updatedRoom = service.selectSong(room.get(), roundId, playerId);
        if (updatedRoom.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedRoom);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{roomId}/start-next-round")
    public ResponseEntity<Object> startNextRound(@PathVariable("roomId") String roomId, @RequestParam("nextBossId") String nextBossId) {
        var room = retrieveRoom(roomId);
        if (room.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ROOM_NOT_FOUND);
        }
        if (room.get().getCurrentRound() == 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GAME_ALREADY_FINISHED);
        }
        var nextBoss = room.get().getRounds().get(room.get().getCurrentRound()).getCurrentBoss();
        if (!nextBoss.getPlayerId().equals(nextBossId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(NOT_CURRENT_BOSS);
        }
        var updatedRoom = service.startNextRound(room.get());
        if (updatedRoom.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedRoom);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private Optional<Room> retrieveRoom(String roomId) {
        return service.findById(RoomId.build(roomId));
    }
}
