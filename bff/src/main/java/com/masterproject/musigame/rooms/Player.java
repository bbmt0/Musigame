package com.masterproject.musigame.rooms;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Player {
    private String playerId;
    private String username;
    private String profilePictureUrl;
}
