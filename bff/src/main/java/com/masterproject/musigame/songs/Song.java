package com.masterproject.musigame.songs;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Song {
    SongId songId;
    String artistNames;
    String title;
    String imageUrl;

    public String toJson() {
        return "{\"songId\":\"" + songId.getValue() + "\",\"artistNames\":\"" + artistNames + "\",\"title\":\"" + title + "\",\"imageUrl\":\"" + imageUrl + "\"}";
    }
}
