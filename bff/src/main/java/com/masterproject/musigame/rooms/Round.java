package com.masterproject.musigame.rooms;

import com.masterproject.musigame.songs.Song;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Round {
    private Integer roundNumber;
    private String sentence;
    private Player currentBoss;
    private Map<String, Song> winningSong;
    private List<Map<String, Song>> songSuggestions;
}
