package com.masterproject.musigame.adapter.rest.artists;

import com.masterproject.musigame.artists.ArtistsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Optional;

import static com.masterproject.musigame.artists.ArtistMother.generateArtistName;
import static com.masterproject.musigame.artists.ArtistMother.artistBuilder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("integration")
@Tag("restApi")
@WebMvcTest(ArtistsController.class)
@DisplayName("Artists controller should")
class ArtistsControllerTests {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ArtistsService service;

    @Test
    @DisplayName("get artists matching keywords")
    void getArtistsMatchingKeywords() throws Exception {
        var nameGenerated = generateArtistName().sample();
        var artists = Collections.singletonList(
                artistBuilder().artistName(nameGenerated).build()
        );
        when(service.findByKeyword(nameGenerated)).thenReturn(Optional.of(artists));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/artists/{nameGenerated}", nameGenerated))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].artistName").value(nameGenerated))
                .andExpect(jsonPath("$[0].imageUrl").value(artists.get(0).getImageUrl()));
    }

    @Test
    @DisplayName("return not found status when no artists match the keyword")
    void returnNotFoundWhenNoArtistsMatchKeyword() throws Exception {
        String keyword = "nonexistent";
        when(service.findByKeyword(keyword)).thenReturn(Optional.empty());
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/artists/{keyword}", keyword))
                .andExpect(status().isNotFound());
    }
}