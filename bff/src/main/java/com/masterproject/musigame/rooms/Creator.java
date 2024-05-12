package com.masterproject.musigame.rooms;

import lombok.Getter;

@Getter
public class Creator extends Player {
    public Creator(String playerId, String username, String profilePictureUrl, Integer score) {
        super(playerId, username, profilePictureUrl, score);
    }
}
