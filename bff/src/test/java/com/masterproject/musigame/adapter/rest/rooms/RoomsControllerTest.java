package com.masterproject.musigame.adapter.rest.rooms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masterproject.musigame.rooms.*;
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

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("integration")
@Tag("restApi")
@WebMvcTest(RoomsController.class)
@DisplayName("Rooms controller should")
class RoomsControllerTest {
    private static final RoomId ROOM_ID = RoomId.generateId();
    @Autowired
    private MockMvc mvc;
    @MockBean
    private RoomsService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("create room with current user as creator")
    void createRoomWithCurrentUserAsCreator() throws Exception {
        Creator creator = new Creator("dummy", "dummyUrl");
        Room mockRoom = Room.builder()
                .roomId(ROOM_ID)
                .game(Game.builder().isGameLaunched(false).gameType(GameType.IMPOSTER).build())
                .creator(creator)
                .players(Collections.singletonList(creator))
                .build();
        when(service.save(creator)).thenReturn(mockRoom);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creator)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(mockRoom)));
    }

    @Test
    @DisplayName("get room with room id")
    void getRoomWithRoomId() throws Exception {
        Creator creator = new Creator("dummy", "dummyUrl");
        Room mockRoom = Room.builder()
                .roomId(ROOM_ID)
                .game(Game.builder().isGameLaunched(false).gameType(GameType.IMPOSTER).build())
                .creator(creator)
                .players(Collections.singletonList(creator))
                .build();

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/{roomId}", ROOM_ID.getValue()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.roomId.value").value(ROOM_ID.getValue()))
                .andExpect(jsonPath("$.game.gameType").value("IMPOSTER"))
                .andExpect(jsonPath("$.game.gameLaunched").value(false))
                .andExpect(jsonPath("$.creator.username").value("dummy"))
                .andExpect(jsonPath("$.creator.profilePictureUrl").value("dummyUrl"))
                .andExpect(jsonPath("$.players[0].username").value("dummy"))
                .andExpect(jsonPath("$.players[0].profilePictureUrl").value("dummyUrl"));
    }

    @Test
    @DisplayName("get not found with wrong room id")
    void getNotFoundWithWrongRoomId() throws Exception {
        Creator creator = new Creator("dummy", "dummyUrl");
        Room mockRoom = Room.builder()
                .roomId(ROOM_ID)
                .game(Game.builder().isGameLaunched(false).gameType(GameType.IMPOSTER).build())
                .creator(creator)
                .players(Collections.singletonList(creator))
                .build();

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/{roomId}", ROOM_ID.getValue()))
                .andExpect(status().isNotFound());
    }

}
