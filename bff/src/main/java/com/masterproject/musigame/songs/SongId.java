package com.masterproject.musigame.songs;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SongId {
    private final String value;

    public static SongId of(@NonNull String value) {
        return new SongId(value);
    }
}
