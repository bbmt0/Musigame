package com.masterproject.musigame.songs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.masterproject.musigame.songs.SongMother.generateTitle;
import static com.masterproject.musigame.songs.SongMother.songBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@Tag("service")
@DisplayName("Songs service should")
class SongsServiceTests {
    private SongsService service;
    private InMemorySongsRepository repository;
    private String KNOWN_TITLE = generateTitle().sample();

    @BeforeEach
    void setUp() {
        repository = new InMemorySongsRepository(KNOWN_TITLE);
        service = new SongsService(repository);
    }

    @Test
    @DisplayName("get a song matching keyword successfully")
    void getASongMatchingKeyword() {
        Song song = songBuilder(KNOWN_TITLE).build();
        var actual = service.findByKeyword(song.getTitle());
        assertThat(actual.get().getFirst().getTitle()).isEqualTo(song.getTitle());
    }

}
