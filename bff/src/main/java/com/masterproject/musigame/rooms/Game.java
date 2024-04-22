package com.masterproject.musigame.rooms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Game {
    private boolean isGameLaunched;
    private GameType gameType;
}
