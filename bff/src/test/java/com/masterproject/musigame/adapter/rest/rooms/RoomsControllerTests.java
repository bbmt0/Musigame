package com.masterproject.musigame.adapter.rest.rooms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masterproject.musigame.rooms.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.stream.Stream;

import static com.masterproject.musigame.rooms.RoomMother.Rooms.ids;
import static com.masterproject.musigame.rooms.RoomMother.generateCreator;
import static com.masterproject.musigame.rooms.RoomMother.roomBuilder;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("integration")
@Tag("restApi")
@WebMvcTest(RoomsController.class)
@DisplayName("Rooms controller should")
class RoomsControllerTests {
    private static final RoomId ROOM_ID = ids().sample();
    @Autowired
    private MockMvc mvc;
    @MockBean
    private RoomsService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("create room with current user as creator")
    void createRoomWithCurrentUserAsCreator() throws Exception {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator).build();

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
        Room mockRoom = roomBuilder(ROOM_ID).build();

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/{roomId}", ROOM_ID.getValue()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.roomId.value").value(ROOM_ID.getValue()));
    }

    @Test
    @DisplayName("get not found with wrong room id")
    void getNotFoundWithWrongRoomId() throws Exception {
        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/{roomId}", ROOM_ID.getValue()))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @MethodSource("gameTypeProvider")
    @DisplayName("start room with room id and creator id")
    void startRoomWithRoomIdAndCreatorId(GameType gameType) throws Exception {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator, gameType).build();

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));
        when(service.startGame(mockRoom, creator, gameType)).thenReturn(Optional.of(mockRoom));

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/start", ROOM_ID.getValue())
                        .param("creatorId", creator.getPlayerId())
                        .param("gameType", gameType.name()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.roomId.value").value(ROOM_ID.getValue()))
                .andExpect(jsonPath("$.game.gameType").value(gameType.name()));
    }

    @ParameterizedTest
    @MethodSource("gameTypeProvider")
    @DisplayName("get not found with wrong room id when starting room")
    void getNotFoundWithWrongRoomIdWhenStartingRoom(GameType gameType) throws Exception {
        Creator creator = generateCreator();

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/start", ROOM_ID.getValue())
                        .param("creatorId", creator.getPlayerId())
                        .param("gameType", gameType.name()))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @MethodSource("gameTypeProvider")
    @DisplayName("get forbidden with wrong creator id when starting room")
    void getForbiddenWithWrongCreatorIdWhenStartingRoom(GameType gameType) throws Exception {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator).build();

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/start", ROOM_ID.getValue())
                        .param("creatorId", "wrongCreatorId")
                        .param("gameType", gameType.name()))
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @MethodSource("gameTypeProvider")
    @DisplayName("get bad request when starting room")
    void getBadRequestWhenStartingRoom(GameType gameType) throws Exception {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator).build();

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));
        when(service.startGame(mockRoom, creator, gameType)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/start", ROOM_ID.getValue())
                        .param("creatorId", creator.getPlayerId())
                        .param("gameType", gameType.name()))
                .andExpect(status().isBadRequest());
    }

    static Stream<Arguments> gameTypeProvider() {
        return Stream.of(
                Arguments.of(GameType.IMPOSTER)
        );
    }


}
