package com.masterproject.musigame.adapter.db.songs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@Tag("db")
@SpringBootTest
@DisplayName("GeniusSongsRepository should")
class GeniusSongsRepositoryTests {
    @Autowired
    private GeniusSongsRepository geniusSongsRepository;

    @Test
    @DisplayName("find a song by keyword")
    void findSongByKeyword() {
        var savedSong = geniusSongsRepository.findByKeyword("Lil Peep");
        assertThat(savedSong).isPresent();
        assertThat(savedSong.get().getFirst().getArtistNames()).contains("Lil Peep");
    }
}
