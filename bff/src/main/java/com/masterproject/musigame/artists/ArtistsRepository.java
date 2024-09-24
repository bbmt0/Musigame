package com.masterproject.musigame.artists;

import jakarta.annotation.Nonnull;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;


public interface ArtistsRepository {
    @Nonnull
    Optional<List<Artist>> findByKeyword(@NonNull String keyword);
}
