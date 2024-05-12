package com.masterproject.musigame.adapter.rest.images;

import com.masterproject.musigame.images.ImageId;
import com.masterproject.musigame.images.ImagesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static com.masterproject.musigame.images.ImageMother.Images.ids;
import static com.masterproject.musigame.images.ImageMother.imageBuilder;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("integration")
@Tag("restApi")
@WebMvcTest(ImagesController.class)
@DisplayName("Images controller should")
class ImagesControllerTests {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ImagesService service;
    private final ImageId KNOWN_IMAGE_ID = ids().sample();

    @Test
    @DisplayName("get images by known id")
    void getSongsMatchingKeywords() throws Exception {
        var image = imageBuilder(KNOWN_IMAGE_ID).build();
        var requestedId = KNOWN_IMAGE_ID.getValue().substring(7);
        when(service.findById(argThat(imageId -> imageId.getValue().equals(KNOWN_IMAGE_ID.getValue())))).thenReturn(Optional.of(image));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/images/{imageId}", requestedId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("imageId.value").value(KNOWN_IMAGE_ID.getValue()))
        ;
    }

    @Test
    @DisplayName("get not found with unknown ImageId")
    void getNotFoundWithUnknownImageId() throws Exception {
        var requestedId = KNOWN_IMAGE_ID.getValue().substring(7);
        when(service.findById(argThat(roomId -> roomId.getValue().equals(KNOWN_IMAGE_ID.getValue())))).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/{roomId}", requestedId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("get all images")
    void getAllImages() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/images"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
