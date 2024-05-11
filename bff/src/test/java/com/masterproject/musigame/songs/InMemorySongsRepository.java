package com.masterproject.musigame.songs;

import jakarta.annotation.Nonnull;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.*;

import static com.masterproject.musigame.songs.SongMother.Songs.ids;
import static com.masterproject.musigame.songs.SongMother.songBuilder;

@NoArgsConstructor
class InMemorySongsRepository implements SongsRepository {

    private final Map<SongId, Song> songs = new LinkedHashMap<>();

    InMemorySongsRepository(String title) {
        songs.put(ids().sample(), songBuilder(title).build());
    }

    @Nonnull
    @Override
    public Optional<List<Song>> findByKeyword(@NonNull String keyword) {
        List<Song> matchingSongs = new ArrayList<>();
        for (Song song : songs.values()) {
            if (song.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                matchingSongs.add(song);
            }
        }
        return matchingSongs.isEmpty() ? Optional.empty() : Optional.of(matchingSongs);

    }
}
