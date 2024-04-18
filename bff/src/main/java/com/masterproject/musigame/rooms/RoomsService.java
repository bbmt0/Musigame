package com.masterproject.musigame.rooms;

import jakarta.annotation.Nonnull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
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
                        .gameType(GameType.valueOf("IMPOSTER"))
                        .isGameLaunched(false)
                        .build())
                .players(new ArrayList<>(Collections.singletonList(creator)))
                .build();
        return repository.save(room);
    }

}
