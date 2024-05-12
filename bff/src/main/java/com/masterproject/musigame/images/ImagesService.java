package com.masterproject.musigame.images;

import jakarta.annotation.Nonnull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImagesService {
    private final ImagesRepository repository;

    @Nonnull
    public Optional<Image> findById(@NonNull ImageId imageId) {
        return repository.findById(imageId);
    }

    @Nonnull
    public Optional<List<Image>> findAll() {
        return repository.findAll();
    }

}
