package com.masterproject.musigame.rooms;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Room {
    private RoomId roomId;
    private Game game;
    private Creator creator;
    private List<Player> players;
    private List<Round> rounds;
}
