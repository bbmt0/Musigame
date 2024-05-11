package com.masterproject.musigame.adapter.db.songs;

import com.masterproject.musigame.songs.Song;
import com.masterproject.musigame.songs.SongsRepository;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GeniusSongsRepository implements SongsRepository {
    private final WebClient webClient;

    @Nonnull
    @Override
    public Optional<List<Song>> findByKeyword(@NonNull String keyword) {
        var response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("api.genius.com")
                        .path("/search")
                        .queryParam("q", keyword)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .blockOptional();
        if (response.isPresent()) {
            var songs = GeniusApiSongConverter.parseResponse(response.get());
            return Optional.of(songs);
        } else {
            return Optional.empty();
        }
    }
}
