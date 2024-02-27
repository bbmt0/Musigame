package com.masterproject.musigame.rooms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Room {
    private RoomId roomId;
    private Game game;
    private Creator creator;
    private List<Player> players;
}
