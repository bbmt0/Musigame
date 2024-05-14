package com.masterproject.musigame.adapter.db.images;

import com.masterproject.musigame.adapter.db.images.FirebaseImagesRepository;
import com.masterproject.musigame.images.ImageId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@Tag("db")
@SpringBootTest
@DisplayName("FirebaseImagesRepository should")
class FirebaseImagesRepositoryTests {
    @Autowired
    private FirebaseImagesRepository firebaseImagesRepository;

    @Test
    @DisplayName("find a song by id")
    void findRoomById() {
        var savedSong = firebaseImagesRepository.findById(ImageId.of("1"));
        var savedSong2 = firebaseImagesRepository.findById(ImageId.of("not-existing-id"));
        assertThat(savedSong.isPresent()).isTrue();
        assertThat(savedSong2.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("find all songs")
    void findAllRooms() {
        var savedSongs = firebaseImagesRepository.findAll();
        assertThat(savedSongs.isPresent()).isTrue();
        assertThat(savedSongs.get().size()).isGreaterThan(0);
    }
}
