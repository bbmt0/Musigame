package com.masterproject.musigame.rooms;

import jakarta.annotation.Nonnull;
import lombok.NonNull;

import java.util.Optional;

public interface RoomsRepository {
    @Nonnull
    Optional<Room> findById(@NonNull RoomId roomId);

    @Nonnull
    Room save(@NonNull Room room);

    @Nonnull
    Room delete(@NonNull RoomId roomId);
}
