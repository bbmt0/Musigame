package com.masterproject.musigame.rooms;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Round {
    private Integer roundNumber;
    private String sentence;
    private Player currentBoss;
}
