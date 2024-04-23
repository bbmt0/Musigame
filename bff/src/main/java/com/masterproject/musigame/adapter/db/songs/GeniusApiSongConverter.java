package com.masterproject.musigame.adapter.db.songs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;
import com.masterproject.musigame.songs.Song;
import com.masterproject.musigame.songs.SongId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeniusApiSongConverter {

    public static List<Song> parseResponse(String jsonResponse) {
        var songs = new ArrayList<Song>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            JsonNode hitsNode = rootNode.path("response").path("hits");
            for (JsonNode hitNode : hitsNode) {
                JsonNode resultNode = hitNode.path("result");
                String id = resultNode.path("id").asText();
                String artistNames = resultNode.path("artist_names").asText();
                String title = resultNode.path("title").asText();
                String headerImageUrl = resultNode.path("header_image_url").asText();
                songs.add(Song.builder()
                        .songId(SongId.of(id))
                        .artistNames(artistNames)
                        .title(title)
                        .imageUrl(headerImageUrl)
                        .build()
                );
            }
        } catch (IOException e) {
            throw new JsonParseException("Error parsing JSON response: " + e.getMessage(), e);
        }
        return songs;
    }
}
