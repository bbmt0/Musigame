package com.masterproject.musigame.adapter.db.images;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.masterproject.musigame.configuration.firebase.FirebaseService;
import com.masterproject.musigame.images.Image;
import com.masterproject.musigame.images.ImageId;
import com.masterproject.musigame.images.ImagesRepository;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FirebaseImagesRepository implements ImagesRepository {
    private final FirebaseService firebaseService;

    @Nonnull
    @Override
    public Optional<Image> findById(@NonNull ImageId imageId) {
        DocumentReference docRef = firebaseService.getDb().collection("images").document(imageId.getValue());
        try {
            DocumentSnapshot documentSnapshot = docRef.get().get();

            if (documentSnapshot.exists()) {
                return Optional.of(mapToImage(documentSnapshot, imageId));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        return Optional.empty();
    }

    private Image mapToImage(DocumentSnapshot documentSnapshot, ImageId imageId) {
        Map<String, Object> data = documentSnapshot.getData();
        if (data != null) {
            String url = (String) data.get("url");
            return Image.builder()
                    .imageId(imageId)
                    .url(url)
                    .build();
        } else {
            return null;
        }
    }
}
