package com.masterproject.musigame.adapter.db.rooms;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.masterproject.musigame.configuration.firebase.FirebaseService;
import com.masterproject.musigame.rooms.*;
import com.masterproject.musigame.songs.Song;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

import static java.lang.Math.toIntExact;

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
            roomData.put("rounds", room.getRounds());
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
            List<Round> rounds = extractRounds(data);

            return Room.builder()
                    .roomId(roomId)
                    .game(game)
                    .creator(creator)
                    .players(players)
                    .rounds(rounds)
                    .build();
        } else {
            return null;
        }
    }

    private Game extractGame(Map<String, Object> data) {
        Map<String, Object> gameData = (Map<String, Object>) data.get("game");
        return Game.builder()
                .isGameLaunched((Boolean) gameData.get("gameLaunched"))
                .gameType(gameData.get("gameType") == null ? null : GameType.valueOf((String) gameData.get("gameType")))
                .build();
    }

    private Player extractPlayer(Map<String, Object> data) {
        Player player = Player.builder()
                .playerId((String) data.get("playerId"))
                .profilePictureUrl((String) data.get("profilePictureUrl"))
                .username((String) data.get("username"))
                .build();
        return new Player(player.getPlayerId(), player.getUsername(), player.getProfilePictureUrl());
    }

    private Creator extractCreator(Map<String, Object> data) {
        Map<String, Object> creatorData = (Map<String, Object>) data.get("creator");
        Player player = extractPlayer(creatorData);
        return new Creator(player.getPlayerId(), player.getUsername(), player.getProfilePictureUrl());
    }

    private List<Player> extractPlayers(Map<String, Object> data) {
        List<Map<String, Object>> playersData = (List<Map<String, Object>>) data.get("players");
        List<Player> players = new ArrayList<>();
        for (Map<String, Object> playerData : playersData) {
            Player player = extractPlayer(playerData);
            players.add(player);
        }
        return players;
    }

    private List<Round> extractRounds(Map<String, Object> data) {
        List<Map<String, Object>> roundsData = (List<Map<String, Object>>) data.get("rounds");
        List<Round> rounds = new ArrayList<>();
        for (Map<String, Object> roundData : roundsData) {
            Player currentBoss;
            if (roundData.get("currentBoss") == null) {
                currentBoss = null;
            } else {
                currentBoss = extractPlayer((Map<String, Object>) roundData.get("currentBoss"));
            }
            Round round = Round.builder()
                    .currentBoss(currentBoss)
                    .roundNumber(toIntExact((Long) roundData.get("roundNumber")))
                    .sentence(roundData.get("sentence") == null ? null : (String) roundData.get("sentence"))
                    .songSuggestions(roundData.get("songSuggestions") == null ? null : (List<Map<String, Song>>) roundData.get("songSuggestions"))
                    .build();
            rounds.add(round);
        }
        return rounds;
    }

}
