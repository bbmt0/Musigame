package com.masterproject.musigame.songs;

import com.masterproject.musigame.rooms.Room;
import com.masterproject.musigame.rooms.RoomId;
import com.masterproject.musigame.rooms.RoomsRepository;
import jakarta.annotation.Nonnull;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor
class InMemorySongsRepository implements SongsRepository {

    private final Map<SongId, Room> songs = new LinkedHashMap<>();

    @Nonnull
    @Override
    public Optional<List<Song>> findByKeyword(@NonNull String keyword) {
        return Optional.empty();
    }
}
