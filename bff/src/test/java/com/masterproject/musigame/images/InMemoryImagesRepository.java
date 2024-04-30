package com.masterproject.musigame.images;

import jakarta.annotation.Nonnull;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.masterproject.musigame.images.ImageMother.imageBuilder;

@NoArgsConstructor
class InMemoryImagesRepository implements ImagesRepository {

    private final Map<ImageId, Image> images = new LinkedHashMap<>();

    InMemoryImagesRepository(ImageId imageId) {
        images.put(imageId, imageBuilder(imageId).build());
    }


    @Nonnull
    @Override
    public Optional<Image> findById(@NonNull ImageId imageId) {
        return Optional.ofNullable(images.get(imageId));
    }
}
