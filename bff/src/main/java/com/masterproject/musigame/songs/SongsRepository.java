package com.masterproject.musigame.songs;

import jakarta.annotation.Nonnull;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;


public interface SongsRepository {
    @Nonnull
    Optional<List<Song>> findByKeyword(@NonNull String keyword);
}
