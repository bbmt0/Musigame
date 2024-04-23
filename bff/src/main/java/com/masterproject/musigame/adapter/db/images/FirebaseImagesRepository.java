package com.masterproject.musigame.adapter.db.images;

import com.masterproject.musigame.images.Image;
import com.masterproject.musigame.images.ImageId;
import com.masterproject.musigame.images.ImagesRepository;
import com.masterproject.musigame.rooms.RoomsRepository;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FirebaseImagesRepository implements ImagesRepository {
    @Nonnull
    @Override
    public Optional<Image> findById(@NonNull ImageId imageId) {
        return Optional.empty();
    }
}
