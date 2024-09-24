package com.masterproject.musigame.adapter.db.artists;

import com.masterproject.musigame.artists.Artist;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("integration")
@Tag("db")
@SpringBootTest
@DisplayName("GeniusArtistsRepository should")
class GeniusArtistsRepositoryTests {
    @Autowired
    private GeniusArtistsRepository geniusArtistsRepository;

    @Test
    @DisplayName("find an artist by keyword")
    void findArtistByKeyword() {
        var savedArtist = geniusArtistsRepository.findByKeyword("Lil Peep");
        assertThat(savedArtist).isPresent();
        assertThat(savedArtist.get().get(0).getArtistName()).contains("Lil Peep");
    }

    @Test
    @DisplayName("not find an artist by keyword")
    void notFindArtistByKeyword() {
        var savedArtist = geniusArtistsRepository.findByKeyword("zduzadazoijdazjdzaijdazidjzaiodjazoidjazd");
        assertThat(savedArtist).isEmpty();
    }

    @Test
    @DisplayName("throws exception when keyword is null")
    void throwsExceptionWhenKeywordIsNull() {
        assertThrows(IllegalArgumentException.class, () -> geniusArtistsRepository.findByKeyword(null));
    }

    @Test
    @DisplayName("return empty optional when WebClient response is empty")
    void returnEmptyOptionalWhenWebClientResponseIsEmpty() {
        var savedArtist = geniusArtistsRepository.findByKeyword("");
        assertThat(savedArtist).isEmpty();
    }
}