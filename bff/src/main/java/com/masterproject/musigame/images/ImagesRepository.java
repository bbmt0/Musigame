package com.masterproject.musigame.images;

import jakarta.annotation.Nonnull;
import lombok.NonNull;

import java.util.Optional;

public interface ImagesRepository {

    @Nonnull
    Optional<Image> findById(@NonNull ImageId imageId);
}
