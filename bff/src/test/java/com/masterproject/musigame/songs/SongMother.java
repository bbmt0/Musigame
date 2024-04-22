package com.masterproject.musigame.songs;

import com.masterproject.musigame.rooms.RoomId;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;

public class SongMother {
    public static Arbitrary<String> generateArtistNames() {
        return Arbitraries.strings().alpha().ofMinLength(5).ofMaxLength(20);
    }

    public static Arbitrary<String> generateTitle() {
        return Arbitraries.strings().alpha().ofMinLength(10).ofMaxLength(50);
    }

    public static Arbitrary<String> generateImageUrl() {
        return Arbitraries.strings().ofMinLength(10).ofMaxLength(100);
    }

    public static class Songs {
        public static Arbitrary<SongId> ids() {
            return Arbitraries.strings().ofMinLength(1).ofMaxLength(10).map(SongId::of);
        }

        @Setter
        @Accessors(fluent = true)
        public static class Builder {
            private SongId songId = ids().sample();
            private String artistNames = generateArtistNames().sample();
            private String title = generateTitle().sample();
            private String imageUrl = generateImageUrl().sample();

            Builder() {
            }

            public Song build() {
                return Song.builder()
                        .songId(songId)
                        .artistNames(artistNames)
                        .title(title)
                        .imageUrl(imageUrl)
                        .build();
            }
        }

    }
}
