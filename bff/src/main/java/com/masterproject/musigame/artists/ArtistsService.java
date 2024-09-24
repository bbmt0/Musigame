package com.masterproject.musigame.artists;

import jakarta.annotation.Nonnull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistsService {
    private final ArtistsRepository repository;

    @Nonnull
    public Optional<List<Artist>> findByKeyword(@NonNull String keyword) {
        return repository.findByKeyword(keyword);
    }

}
