package com.masterproject.musigame.rooms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Room {
    private RoomId roomId;
    private Game game;
    private Creator creator;
    private List<Player> players;
}
