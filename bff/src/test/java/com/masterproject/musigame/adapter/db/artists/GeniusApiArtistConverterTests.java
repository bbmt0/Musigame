package com.masterproject.musigame.adapter.db.artists;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.masterproject.musigame.artists.Artist;
import com.masterproject.musigame.artists.ArtistId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("unitTest")
@Tag("converter")
@DisplayName("GeniusApiArtistConverter should ")
class GeniusApiArtistConverterTests {

    @Test
    @DisplayName("convert successfully a correct JSON to an artist")
    void convertSuccessfullyAJsonToAnArtist() throws JsonProcessingException {
        var jsonResponse = "{\n" +
                "    \"meta\": {\n" +
                "        \"status\": 200\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "        \"hits\": [\n" +
                "            {\n" +
                "                \"highlights\": [],\n" +
                "                \"index\": \"artist\",\n" +
                "                \"type\": \"artist\",\n" +
                "                \"result\": {\n" +
                "                    \"primary_artist\": {\n" +
                "                        \"id\": 569922,\n" +
                "                        \"name\": \"Lil Peep\",\n" +
                "                        \"image_url\": \"https://images.genius.com/919c7ba130d3861740cbe7fbd7f83c59.1000x1000x1.jpg\"\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        var expected = Artist.builder()
                .artistId(ArtistId.of("569922"))
                .artistName("Lil Peep")
                .imageUrl("https://images.genius.com/919c7ba130d3861740cbe7fbd7f83c59.1000x1000x1.jpg")
                .build();
        var actual = GeniusApiArtistConverter.parseResponse(jsonResponse);
        assertThat(actual).isNotEmpty();
        assertThat(expected.getArtistId().getValue()).isEqualTo(actual.get(0).getArtistId().getValue());
        assertThat(expected.getArtistName()).isEqualTo(actual.get(0).getArtistName());
        assertThat(expected.getImageUrl()).isEqualTo(actual.get(0).getImageUrl());
    }

    @Test
    @DisplayName("throws an exception when converting an incorrect JSON")
    void throwsExceptionWhenConvertingAnIncorrectJson() {
        var jsonResponse = "badJson";
        assertThatThrownBy(() -> GeniusApiArtistConverter.parseResponse(jsonResponse))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error parsing JSON response");
    }
}