package com.masterproject.musigame.rooms;

import jakarta.annotation.Nonnull;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor
class InMemoryRoomsRepository implements RoomsRepository {

    private final Map<RoomId, Room> rooms = new LinkedHashMap<>();

    @Nonnull
    @Override
    public Optional<Room> findById(@NonNull RoomId roomId) {
        return Optional.ofNullable(rooms.get(roomId));
    }

    @Nonnull
    @Override
    public Room save(@NonNull Room room) {
        rooms.put(room.getRoomId(), room);
        return room;
    }
}
