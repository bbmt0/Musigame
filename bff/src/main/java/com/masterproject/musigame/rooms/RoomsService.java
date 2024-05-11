package com.masterproject.musigame.rooms;

import jakarta.annotation.Nonnull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

        Room room = Room.builder()
                .roomId(roomId)
                .creator(creator)
                .game(Game.builder()
                        .gameType(null)
                        .isGameLaunched(false)
                        .build())
                .players(new ArrayList<>(Collections.singletonList(creator)))
                .rounds(generateRounds(creator))
                .build();
        room.getRounds().getFirst().setCurrentBoss(creator);
        return repository.save(room);
    }

    @Nonnull
    public Optional<Room> startGame(@NonNull Room room, GameType gameType) {
        room.getGame().setGameLaunched(true);
        room.getGame().setGameType(gameType);
        return Optional.of(repository.save(room));
    }

    @Nonnull
    public Optional<Room> submitSentence(@NonNull Room room, @NonNull Integer roundId, @NonNull String sentence) {
        room.getRounds().get(roundId).setSentence(sentence);
        return Optional.of(repository.save(room));
    }

    @Nonnull
    public Optional<Room> join(@NonNull Room room, @NonNull Player player) {
        room.getPlayers().add(player);
        return Optional.of(repository.save(room));
    }

    private List<Round> generateRounds(Player player) {
        List<Round> rounds = new ArrayList<>(3);
        rounds.add(Round.builder().build());
        rounds.add(Round.builder().build());
        rounds.add(Round.builder().build());
        rounds.getFirst().setCurrentBoss(player);
        return rounds;
    }

}
