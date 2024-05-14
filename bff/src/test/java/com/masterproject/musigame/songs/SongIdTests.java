package com.masterproject.musigame.songs;

import com.masterproject.musigame.images.ImageId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("unit")
@Tag("domain")
@DisplayName("SongId should")
class SongIdTests {
    @Test
    @DisplayName("create SongId successfully")
    void buildSongId() {
        String validSongIdValue = "AbCd1";
        SongId songId = SongId.of(validSongIdValue);
        assertEquals(validSongIdValue, songId.getValue());
    }

    @Test
    @DisplayName("throw an exception when the value is null")
    void throwExceptionWhenValueIsNull() {
        assertThrows(IllegalArgumentException.class, () -> SongId.of(null));
    }
}
