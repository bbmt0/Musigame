package com.masterproject.musigame.adapter.db.rooms;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.masterproject.musigame.configuration.firebase.FirebaseService;
import com.masterproject.musigame.rooms.*;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class FirebaseRoomsRepository implements RoomsRepository {
    private final FirebaseService firebaseService;


    @Nonnull
    @Override
    public Room save(@NonNull Room room) {
        try {
            DocumentReference docRef = firebaseService.getDb().collection("rooms").document(room.getRoomId().getValue());

            Map<String, Object> roomData = new HashMap<>();
            roomData.put("id", room.getRoomId().getValue());
            roomData.put("game", room.getGame());
            roomData.put("creator", room.getCreator());
            roomData.put("players", room.getPlayers());
            docRef.set(roomData);

            return room;
        } catch (Exception e) {
            throw new FirebaseRoomDocumentNotSaved();
        }
    }

    @Nonnull
    @Override
    public Optional<Room> findById(@NonNull RoomId roomId) {
        DocumentReference docRef = firebaseService.getDb().collection("rooms").document(roomId.getValue());
        try {
            DocumentSnapshot documentSnapshot = docRef.get().get();

            if (documentSnapshot.exists()) {
                return Optional.of(mapToRoom(documentSnapshot, roomId));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        return Optional.empty();
    }

    private Room mapToRoom(DocumentSnapshot documentSnapshot, RoomId roomId) {
        Map<String, Object> data = documentSnapshot.getData();
        if (data != null) {
            Game game = extractGame(data);
            Creator creator = extractCreator(data);
            List<Player> players = extractPlayers(data);

            return Room.builder()
                    .roomId(roomId)
                    .game(game)
                    .creator(creator)
                    .players(players)
                    .build();
        } else {
            return null;
        }
    }

    private Game extractGame(Map<String, Object> data) {
        Map<String, Object> gameData = (Map<String, Object>) data.get("game");
        return Game.builder()
                .isGameLaunched((Boolean) gameData.get("gameLaunched"))
                .gameType(GameType.valueOf((String) gameData.get("gameType")))
                .build();
    }

    private Creator extractCreator(Map<String, Object> data) {
        Map<String, Object> creatorData = (Map<String, Object>) data.get("creator");
        Player player = Player.builder()
                .playerId((String) creatorData.get("playerId"))
                .profilePictureUrl((String) creatorData.get("profilePictureUrl"))
                .username((String) creatorData.get("username"))
                .build();
        return new Creator(player.getPlayerId(), player.getUsername(), player.getProfilePictureUrl());
    }

    private List<Player> extractPlayers(Map<String, Object> data) {
        List<Map<String, String>> playersData = (List<Map<String, String>>) data.get("players");
        List<Player> players = new ArrayList<>();
        for (Map<String, String> playerData : playersData) {
            String playerId = playerData.get("playerId.value");
            String profilePictureUrl = playerData.get("profilePictureUrl");
            String username = playerData.get("username");
            Player player = Player.builder()
                    .playerId(playerId)
                    .profilePictureUrl(profilePictureUrl)
                    .username(username)
                    .build();
            players.add(player);
        }
        return players;
    }

}
