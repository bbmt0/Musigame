package com.masterproject.musigame.artists;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ArtistId {
    private final String value;

    public static ArtistId of(@NonNull String value) {
        return new ArtistId(value);
    }
}
