package com.masterproject.musigame.songs;

import jakarta.annotation.Nonnull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongsService {
    private final SongsRepository repository;

    @Nonnull
    public Optional<List<Song>> findByKeyword(@NonNull String keyword) {
        return repository.findByKeyword(keyword);
    }

}
