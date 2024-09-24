package com.masterproject.musigame.artists;

import com.masterproject.musigame.songs.Song;
import com.masterproject.musigame.songs.SongId;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;

public class ArtistMother {
    public static Arbitrary<String> generateArtistName() {
        return Arbitraries.strings().alpha().ofMinLength(5).ofMaxLength(20);
    }


    public static Arbitrary<String> generateImageUrl() {
        return Arbitraries.strings().ofMinLength(10).ofMaxLength(100);
    }

    public static Artists.Builder artistBuilder() {
        return new Artists.Builder();
    }


    public static class Artists {
        public static Arbitrary<ArtistId> ids() {
            return Arbitraries.strings().ofMinLength(1).ofMaxLength(10).map(ArtistId::of);
        }

        @Setter
        @Accessors(fluent = true)
        public static class Builder {
            private ArtistId artistId = ids().sample();
            private String artistName = generateArtistName().sample();
            private String imageUrl = generateImageUrl().sample();

            Builder() {
            }


            public Artist build() {
                return Artist.builder()
                        .artistName(artistName)
                        .artistId(artistId)
                        .imageUrl(imageUrl)
                        .build();
            }
        }

    }
}
