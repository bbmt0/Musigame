package com.masterproject.musigame.adapter.db.songs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.masterproject.musigame.songs.Song;
import com.masterproject.musigame.songs.SongId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unitTest")
@Tag("converter")
@DisplayName("GeniusApiSongConverter should ")
class GeniusApiSongConverterTests {

    @Test
    @DisplayName("")
    void convertSuccessfullyAJsonToASong() throws JsonProcessingException {
        var jsonResponse = "{\n" +
                "    \"meta\": {\n" +
                "        \"status\": 200\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "        \"hits\": [\n" +
                "            {\n" +
                "                \"highlights\": [],\n" +
                "                \"index\": \"song\",\n" +
                "                \"type\": \"song\",\n" +
                "                \"result\": {\n" +
                "                    \"annotation_count\": 7,\n" +
                "                    \"api_path\": \"/songs/2353006\",\n" +
                "                    \"artist_names\": \"Lil Peep\",\n" +
                "                    \"full_title\": \"Star Shopping by Lil Peep\",\n" +
                "                    \"header_image_thumbnail_url\": \"https://images.genius.com/2fb0c060f277b3a6e82d8671c0841724.300x300x1.jpg\",\n" +
                "                    \"header_image_url\": \"https://images.genius.com/2fb0c060f277b3a6e82d8671c0841724.500x500x1.jpg\",\n" +
                "                    \"id\": 2353006,\n" +
                "                    \"lyrics_owner_id\": 2447522,\n" +
                "                    \"lyrics_state\": \"complete\",\n" +
                "                    \"path\": \"/Lil-peep-star-shopping-lyrics\",\n" +
                "                    \"pyongs_count\": 1377,\n" +
                "                    \"relationships_index_url\": \"https://genius.com/Lil-peep-star-shopping-sample\",\n" +
                "                    \"release_date_components\": {\n" +
                "                        \"year\": 2015,\n" +
                "                        \"month\": 8,\n" +
                "                        \"day\": 17\n" +
                "                    },\n" +
                "                    \"release_date_for_display\": \"August 17, 2015\",\n" +
                "                    \"release_date_with_abbreviated_month_for_display\": \"Aug. 17, 2015\",\n" +
                "                    \"song_art_image_thumbnail_url\": \"https://images.genius.com/189dcc196b379d60d407afeabb301c35.300x300x1.png\",\n" +
                "                    \"song_art_image_url\": \"https://images.genius.com/189dcc196b379d60d407afeabb301c35.1000x1000x1.png\",\n" +
                "                    \"stats\": {\n" +
                "                        \"unreviewed_annotations\": 1,\n" +
                "                        \"concurrents\": 2,\n" +
                "                        \"hot\": false,\n" +
                "                        \"pageviews\": 4441238\n" +
                "                    },\n" +
                "                    \"title\": \"Star Shopping\",\n" +
                "                    \"title_with_featured\": \"Star Shopping\",\n" +
                "                    \"url\": \"https://genius.com/Lil-peep-star-shopping-lyrics\",\n" +
                "                    \"featured_artists\": [],\n" +
                "                    \"primary_artist\": {\n" +
                "                        \"api_path\": \"/artists/569922\",\n" +
                "                        \"header_image_url\": \"https://images.genius.com/e65e57771e17b0da38257d96b846f680.1000x563x1.jpg\",\n" +
                "                        \"id\": 569922,\n" +
                "                        \"image_url\": \"https://images.genius.com/919c7ba130d3861740cbe7fbd7f83c59.1000x1000x1.jpg\",\n" +
                "                        \"is_meme_verified\": false,\n" +
                "                        \"is_verified\": true,\n" +
                "                        \"name\": \"Lil Peep\",\n" +
                "                        \"url\": \"https://genius.com/artists/Lil-peep\",\n" +
                "                        \"iq\": 2519\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        var expected = Song.builder()
                .songId(SongId.of("2353006"))
                .artistNames("Lil Peep")
                .title("Star Shopping")
                .imageUrl("https://images.genius.com/2fb0c060f277b3a6e82d8671c0841724.500x500x1.jpg")
                .build();
        var actual = GeniusApiSongConverter.parseResponse(jsonResponse);
        assertThat(expected.getSongId().getValue()).isEqualTo(actual.getFirst().getSongId().getValue());
        assertThat(expected.getTitle()).isEqualTo(actual.getFirst().getTitle());
        assertThat(expected.getArtistNames()).isEqualTo(actual.getFirst().getArtistNames());
        assertThat(expected.getImageUrl()).isEqualTo(actual.getFirst().getImageUrl());

    }

}
