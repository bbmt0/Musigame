package com.masterproject.musigame.songs;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Song {
    SongId songId;
    String artistNames;
    String title;
    String imageUrl;

    public String toJson() {
        return "{\"songId\":\"" + songId.getValue() + "\",\"artistNames\":\"" + artistNames + "\",\"title\":\"" + title + "\",\"imageUrl\":\"" + imageUrl + "\"}";
    }
}
