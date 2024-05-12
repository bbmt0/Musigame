package com.masterproject.musigame.adapter.db.rooms;

import com.masterproject.musigame.adapter.db.images.FirebaseImagesRepository;
import com.masterproject.musigame.configuration.firebase.FirebaseService;
import com.masterproject.musigame.images.ImageId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@Tag("db")
@WebMvcTest(FirebaseImagesRepository.class)
@DisplayName("FirebaseImagesRepository should")
@Import(FirebaseService.class)
class FirebaseImagesRepositoryTests {
    @Autowired
    private FirebaseImagesRepository firebaseImagesRepository;

    @Test
    @DisplayName("find a song by id")
    void findRoomById(){
        var savedSong = firebaseImagesRepository.findById(ImageId.of("1"));
        var savedSong2 = firebaseImagesRepository.findById(ImageId.of("not-existing-id"));
        assertThat(savedSong.isPresent()).isTrue();
        assertThat(savedSong2.isEmpty()).isTrue();
    }
}
