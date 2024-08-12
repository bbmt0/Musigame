package com.masterproject.musigame.rooms;

import com.masterproject.musigame.songs.Song;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoomsService {
    private final RoomsRepository repository;

    @Nonnull
    public Optional<Room> findById(@NonNull RoomId roomId) {
        return repository.findById(roomId);
    }

    @Nonnull
    public Room save(@NonNull Creator creator) {
        var roomId = RoomId.generateId();
        creator.setScore(0);
        Room room = Room.builder()
                .roomId(roomId)
                .creator(creator)
                .currentRound(1)
                .numberOfRound(null)
                .rounds(null)
                .game(Game.builder()
                        .gameType(null)
                        .isGameLaunched(false)
                        .build())
                .players(new ArrayList<>(Collections.singletonList(creator)))
                .build();
        return repository.save(room);
    }

    @Nonnull
    public Optional<Room> startGame(@NonNull Room room, GameType gameType, Integer numberOfRounds) {
        room.getGame().setGameLaunched(true);
        room.getGame().setGameType(gameType);
        room.setNumberOfRound(numberOfRounds);
        room.setRounds(generateRounds(room.getCreator(), numberOfRounds));
        return Optional.of(repository.save(room));
    }

    @Nonnull
    public Optional<Room> submitSentence(@NonNull Room room, @NonNull Integer roundId, @NonNull String sentence) {
        room.getRounds().get(roundId - 1).setSentence(sentence);
        return Optional.of(repository.save(room));
    }

    @Nonnull
    public Optional<Room> submitSong(@NonNull Room room, @NonNull Integer roundId, @NonNull String playerId, @NonNull Song song) {
        List<Map<String, Song>> songs = room.getRounds().get(roundId - 1).getSongSuggestions() == null ?
                new ArrayList<>() : room.getRounds().get(roundId - 1).getSongSuggestions();
        songs.add(Collections.singletonMap(playerId, song));
        room.getRounds().get(roundId - 1).setSongSuggestions(songs);
        return Optional.of(repository.save(room));
    }

    @Nonnull
    public Optional<Room> selectSong(@NonNull Room room, @NonNull Integer roundId, @NonNull String playerId) {
        var round = room.getRounds().get(roundId - 1);
        var mapSongPlayer = round.getSongSuggestions().stream()
                .filter(map -> map.containsKey(playerId))
                .findFirst()
                .orElseThrow();
        round.setWinningSong(mapSongPlayer);

        var player = room.getPlayers().stream()
                .filter(p -> p.getPlayerId().equals(playerId))
                .findFirst()
                .orElseThrow();
        player.setScore(player.getScore() + 1);

        var nextBoss = room.getPlayers().stream()
                .filter(p -> p.getPlayerId().equals(playerId))
                .findFirst()
                .orElseThrow();
        if (roundId < room.getNumberOfRound()) {
            room.getRounds().get(roundId).setCurrentBoss(nextBoss);
        }
        return Optional.of(repository.save(room));
    }

    @Nonnull
    public Optional<Room> join(@NonNull Room room, @NonNull Player player) {
        player.setScore(0);
        room.getPlayers().add(player);
        return Optional.of(repository.save(room));
    }

    @Nonnull
    public Optional<Room> leave(@NonNull Room room, @NonNull Player player) {
        room.getPlayers().removeIf(p -> p.getPlayerId().equals(player.getPlayerId()));
        return Optional.of(repository.save(room));
    }

    @Nonnull
    public Optional<Room> startNextRound(@NonNull Room room) {
        room.setCurrentRound(room.getCurrentRound() + 1);
        return Optional.of(repository.save(room));
    }

    private List<Round> generateRounds(Player player, Integer numberOfRounds) {
        List<Round> rounds = new ArrayList<>(numberOfRounds);
        for (int i = 0; i < numberOfRounds; i++) {
            rounds.add(Round.builder().build());
            rounds.get(i).setRoundNumber(i + 1);
        }
        rounds.getFirst().setCurrentBoss(player);
        return rounds;
    }
}
