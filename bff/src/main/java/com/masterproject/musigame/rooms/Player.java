package com.masterproject.musigame.rooms;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Player {
    private PlayerId playerId;
    private String username;
    private String profilePictureUrl;
}
