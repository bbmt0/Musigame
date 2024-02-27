package com.masterproject.musigame.rooms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Game {
    private boolean isGameLaunched;
    private GameType gameType;
}
