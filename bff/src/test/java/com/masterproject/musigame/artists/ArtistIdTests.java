package com.masterproject.musigame.artists;

import com.masterproject.musigame.songs.SongId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("unit")
@Tag("domain")
@DisplayName("SongId should")
class ArtistIdTests {
    @Test
    @DisplayName("create ArtistId successfully")
    void buildArtistd() {
        String validArtistIdValue = "AbCd1";
        ArtistId artistId = ArtistId.of(validArtistIdValue);
        assertEquals(validArtistIdValue, artistId.getValue());
    }

    @Test
    @DisplayName("throw an exception when the value is null")
    void throwExceptionWhenValueIsNull() {
        assertThrows(IllegalArgumentException.class, () -> ArtistId.of(null));
    }
}
