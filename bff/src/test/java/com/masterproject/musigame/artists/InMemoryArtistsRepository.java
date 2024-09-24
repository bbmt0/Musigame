package com.masterproject.musigame.artists;

import jakarta.annotation.Nonnull;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.*;

import static com.masterproject.musigame.artists.ArtistMother.Artists.ids;
import static com.masterproject.musigame.artists.ArtistMother.artistBuilder;

@NoArgsConstructor
class InMemoryArtistsRepository implements ArtistsRepository {

    private final Map<ArtistId, Artist> artists = new LinkedHashMap<>();

    InMemoryArtistsRepository(String artistName) {
        artists.put(ids().sample(), artistBuilder().artistName(artistName).build());
    }

    @Nonnull
    @Override
    public Optional<List<Artist>> findByKeyword(@NonNull String keyword) {
        List<Artist> matchingArtists = new ArrayList<>();
        for (Artist artist : artists.values()) {
            if (artist.getArtistName().toLowerCase().contains(keyword.toLowerCase())) {
                matchingArtists.add(artist);
            }
        }
        return matchingArtists.isEmpty() ? Optional.empty() : Optional.of(matchingArtists);
    }
}