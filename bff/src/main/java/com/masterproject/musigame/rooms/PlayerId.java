package com.masterproject.musigame.rooms;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlayerId {
    private final String value;

    public static PlayerId of(@NonNull String value) {
        return new PlayerId(value);
    }
}
