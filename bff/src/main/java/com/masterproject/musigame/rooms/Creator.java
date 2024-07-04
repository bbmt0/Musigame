package com.masterproject.musigame.rooms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Creator extends Player {
    public Creator(String playerId, String username, String profilePictureUrl, Integer score) {
        super(playerId, username, profilePictureUrl, score);
    }
}
