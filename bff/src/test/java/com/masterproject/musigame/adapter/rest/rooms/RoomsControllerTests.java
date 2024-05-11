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

import java.util.ArrayList;
import java.util.List;
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
        when(service.startGame(mockRoom, gameType)).thenReturn(Optional.of(mockRoom));

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
                .andExpect(status().isForbidden())
                .andExpect(content().string("Player is not the creator"));
    }

    @ParameterizedTest
    @MethodSource("gameTypeProvider")
    @DisplayName("get bad request when starting room")
    void getBadRequestWhenStartingRoom(GameType gameType) throws Exception {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator).build();

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));
        when(service.startGame(mockRoom, gameType)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/start", ROOM_ID.getValue())
                        .param("creatorId", creator.getPlayerId())
                        .param("gameType", gameType.name()))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("roundIdProvider")
    @DisplayName("submit sentence with room id, current boss id, round id and sentence")
    void submitSentenceWithRoomIdCurrentBossIdRoundIdAndSentence() throws Exception {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator).build();
        int roundId = 0;
        String sentence = "sentence";

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));

        mockRoom.getRounds().get(roundId).setCurrentBoss(creator);
        mockRoom.getRounds().get(roundId).setSentence(sentence);

        when(service.submitSentence(mockRoom, roundId, sentence)).thenReturn(Optional.of(mockRoom));

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/submit-sentence", ROOM_ID.getValue())
                        .param("currentBossId", creator.getPlayerId())
                        .param("roundId", String.valueOf(roundId))
                        .param("sentence", sentence))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.roomId.value").value(ROOM_ID.getValue()))
                .andExpect(jsonPath("$.rounds[" + roundId + "].sentence").value(sentence));
    }

    @Test
    @DisplayName("join player to room with room id")
    void joinPlayerToRoomWithRoomId() throws Exception {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator).buildNoPlayers();
        Player player = RoomMother.generatePlayer();

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));

        var updatedRoom = Room.builder()
                .game(mockRoom.getGame())
                .creator(mockRoom.getCreator())
                .roomId(mockRoom.getRoomId())
                .players(mockRoom.getPlayers())
                .rounds(mockRoom.getRounds())
                .build();
        var players = new ArrayList<>(updatedRoom.getPlayers());
        players.add(player);
        updatedRoom.setPlayers(players);

        when(service.join(mockRoom, player)).thenReturn(Optional.of(updatedRoom));

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/join", ROOM_ID.getValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.roomId.value").value(ROOM_ID.getValue()))
                .andExpect(jsonPath("$.players[1].playerId").value(player.getPlayerId()));
    }

    @Test
    @DisplayName("join player to room with unknown room id")
    void joinPlayerToRoomWithUnknownRoomId() throws Exception {
        Player player = RoomMother.generatePlayer();

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/join", ROOM_ID.getValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("join player when player is already in room")
    void joinPlayerWhenPlayerIsAlreadyInRoom() throws Exception {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator).buildNoPlayers();
        Player player = RoomMother.generatePlayer();
        var players = new ArrayList<>(mockRoom.getPlayers());
        players.add(player);
        mockRoom.setPlayers(players);

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/join", ROOM_ID.getValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Player already in room"));
    }

    @Test
    @DisplayName("join player when room is full")
    void joinPlayerWhenRoomIsFull() throws Exception {
        Room mockRoom = generateARoomFullOfPlayers();
        Player player = RoomMother.generatePlayer();

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/join", ROOM_ID.getValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Room is full"));
    }

    static Room generateARoomFullOfPlayers() {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator).buildNoPlayers();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            players.add(RoomMother.generatePlayer());
        }
        mockRoom.setPlayers(players);
        return mockRoom;
    }

    static Stream<Arguments> gameTypeProvider() {
        return Stream.of(
                Arguments.of(GameType.IMPOSTER)
        );
    }

    static Stream<Arguments> roundIdProvider() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(1),
                Arguments.of(2)
        );
    }


}
