package com.masterproject.musigame.artists;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Artist {
    ArtistId artistId;
    String artistName;
    String imageUrl;

   // public String toJson() {
   //     return "{\"songId\":\"" + artistId.getValue() + "\",\"artistName\":\"" + artistName + "\",\"imageUrl\":\"" + imageUrl + "\"}";
   // }
}
