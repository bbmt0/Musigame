package com.masterproject.musigame.rooms;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Game {
    private boolean isGameLaunched;
    private GameType gameType;
}
