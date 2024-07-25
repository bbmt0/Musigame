package com.masterproject.musigame.adapter.rest.songs;

import com.masterproject.musigame.songs.SongsService;
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

import static com.masterproject.musigame.songs.SongMother.generateTitle;
import static com.masterproject.musigame.songs.SongMother.songBuilder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("integration")
@Tag("restApi")
@WebMvcTest(SongsController.class)
@DisplayName("Songs controller should")
class SongsControllerTests {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private SongsService service;

    @Test
    @DisplayName("get songs matching keywords")
    void getSongsMatchingKeywords() throws Exception {
        var titleGenerated = generateTitle().sample();
        var songs = Collections.singletonList(
                songBuilder(titleGenerated).build()
        );
        when(service.findByKeyword(titleGenerated)).thenReturn(Optional.of(songs));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/songs/{titleGenerated}", titleGenerated))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value(titleGenerated))
                .andExpect(jsonPath("$[0].artistNames").value(songs.getFirst().getArtistNames()))
                .andExpect(jsonPath("$[0].title").value(songs.getFirst().getTitle()))
                .andExpect(jsonPath("$[0].imageUrl").value(songs.getFirst().getImageUrl()))
        ;
    }

    @Test
    @DisplayName("return not found status when no songs match the keyword")
    void returnNotFoundWhenNoSongsMatchKeyword() throws Exception {
        String keyword = "nonexistent";
        when(service.findByKeyword(keyword)).thenReturn(Optional.empty());
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/songs/{keyword}", keyword))
                .andExpect(status().isNotFound());
    }
}
