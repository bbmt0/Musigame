package com.masterproject.musigame.adapter.db.songs;

import com.masterproject.musigame.songs.Song;
import com.masterproject.musigame.songs.SongsRepository;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GeniusSongsRepository implements SongsRepository {
    @Nonnull
    @Override
    public Optional<List<Song>> findByKeyword(@NonNull String keyword) {
        return Optional.empty();
    }
}
