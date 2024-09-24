package com.masterproject.musigame.artists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.masterproject.musigame.artists.ArtistMother.generateArtistName;
import static com.masterproject.musigame.artists.ArtistMother.artistBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("unit")
@Tag("service")
@DisplayName("Artists service should")
class ArtistServiceTests {
    private ArtistsService service;
    private InMemoryArtistsRepository repository;
    private final String KNOWN_NAME = generateArtistName().sample();

    @BeforeEach
    void setUp() {
        repository = new InMemoryArtistsRepository(KNOWN_NAME);
        service = new ArtistsService(repository);
    }

    @Test
    @DisplayName("get an artist matching keyword successfully")
    void getAnArtistMatchingKeyword() {
        Artist artist = artistBuilder().artistName(KNOWN_NAME).build();
        var actual = service.findByKeyword(artist.getArtistName());
        assertThat(actual.get().get(0).getArtistName()).isEqualTo(artist.getArtistName());
    }

    @Test
    @DisplayName("throw an exception when the keyword is null")
    void throwExceptionWhenKeywordIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.findByKeyword(null));
    }
}