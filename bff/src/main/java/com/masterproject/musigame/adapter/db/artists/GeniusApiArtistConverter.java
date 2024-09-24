package com.masterproject.musigame.adapter.db.artists;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;
import com.masterproject.musigame.artists.Artist;
import com.masterproject.musigame.artists.ArtistId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeniusApiArtistConverter {

    private static final Pattern COLLAB_PATTERN = Pattern.compile("(?i)(feat|&)");

    public static List<Artist> parseResponse(String jsonResponse) {
        Set<String> seenArtistIds = new HashSet<>();
        List<Artist> artists = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            JsonNode hitsNode = rootNode.path("response").path("hits");

            for (JsonNode hitNode : hitsNode) {
                JsonNode resultNode = hitNode.path("result");
                JsonNode primaryArtistNode = resultNode.path("primary_artist");

                String id = primaryArtistNode.path("id").asText();
                String name = primaryArtistNode.path("name").asText();
                String imageUrl = primaryArtistNode.path("image_url").asText();

                if (!seenArtistIds.contains(id) && !COLLAB_PATTERN.matcher(name).find()) {
                    artists.add(Artist.builder()
                            .artistId(ArtistId.of(id))
                            .artistName(name)
                            .imageUrl(imageUrl)
                            .build());
                    seenArtistIds.add(id);
                }
            }
        } catch (IOException e) {
            throw new JsonParseException("Error parsing JSON response: " + e.getMessage(), e);
        }
        return artists;
    }
}
