package com.masterproject.musigame.adapter.rest.rooms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masterproject.musigame.rooms.*;
import com.masterproject.musigame.songs.Song;
import com.masterproject.musigame.songs.SongMother;
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

import java.util.*;
import java.util.stream.Stream;

import static com.masterproject.musigame.rooms.RoomMother.Rooms.ids;
import static com.masterproject.musigame.rooms.RoomMother.*;
import static org.mockito.ArgumentMatchers.any;
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
    void submitSentenceWithRoomIdCurrentBossIdRoundIdAndSentence(Integer roundId) throws Exception {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator).build();
        String sentence = "sentence";
        var roundIdMinusOne = roundId - 1;

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));

        mockRoom.getRounds().get(roundIdMinusOne).setCurrentBoss(creator);
        mockRoom.getRounds().get(roundIdMinusOne).setSentence(sentence);

        when(service.submitSentence(mockRoom, roundId, sentence)).thenReturn(Optional.of(mockRoom));

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/submit-sentence", ROOM_ID.getValue())
                        .param("currentBossId", creator.getPlayerId())
                        .param("roundId", String.valueOf(roundId))
                        .param("sentence", sentence))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.roomId.value").value(ROOM_ID.getValue()))
                .andExpect(jsonPath("$.rounds[" + roundIdMinusOne + "].sentence").value(sentence));
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

    @ParameterizedTest
    @MethodSource("roundIdProvider")
    @DisplayName("submit a song successfully")
    void submitSongSuccessfully(Integer roundId) throws Exception {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator).buildNoPlayers();
        Player player = RoomMother.generatePlayer();
        Song song = SongMother.songBuilder().build();

        var players = new ArrayList<>(mockRoom.getPlayers());
        players.add(player);
        mockRoom.setPlayers(players);


        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));

        if (mockRoom.getRounds().get(roundId - 1).getCurrentBoss() == null) {
            mockRoom.getRounds().get(roundId - 1).setCurrentBoss(creator);
        }


        var updatedRoom = Room.builder()
                .game(mockRoom.getGame())
                .creator(mockRoom.getCreator())
                .roomId(mockRoom.getRoomId())
                .players(mockRoom.getPlayers())
                .rounds(generateRounds(creator))
                .build();

        List<Map<String, Song>> songs = updatedRoom.getRounds().get(roundId - 1).getSongSuggestions() == null ?
                new ArrayList<>() : updatedRoom.getRounds().get(roundId - 1).getSongSuggestions();
        songs.add(Collections.singletonMap(player.getPlayerId(), song));
        updatedRoom.getRounds().get(roundId - 1).setSongSuggestions(songs);

        when(service.submitSong(any(Room.class), any(Integer.class), any(String.class), any(Song.class)))
                .thenReturn(Optional.of(updatedRoom));

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/submit-song", ROOM_ID.getValue())
                        .param("playerId", player.getPlayerId())
                        .param("roundId", String.valueOf(roundId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(song.toJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId.value").value(ROOM_ID.getValue()))
                .andExpect(jsonPath("$.rounds[" + (roundId - 1) + "].songSuggestions[0]." + player.getPlayerId() + ".title").value(song.getTitle()));
    }

    @Test
    @DisplayName("submit a song with unknown room id")
    void submitSongWithUnknownRoomId() throws Exception {
        Player player = RoomMother.generatePlayer();
        Song song = SongMother.songBuilder().build();

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/submit-song", ROOM_ID.getValue())
                        .param("playerId", player.getPlayerId())
                        .param("roundId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(song.toJson()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("submit a song when player is the current boss")
    void submitSongWhenPlayerIsTheCurrentBoss() throws Exception {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator).buildNoPlayers();
        Player player = RoomMother.generatePlayer();
        Song song = SongMother.songBuilder().build();

        var players = new ArrayList<>(mockRoom.getPlayers());
        players.add(player);
        mockRoom.setPlayers(players);

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/submit-song", ROOM_ID.getValue())
                        .param("playerId", creator.getPlayerId())
                        .param("roundId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(song.toJson()))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Player is the current boss"));
    }

    @Test
    @DisplayName("submit a song with wrong round id")
    void submitSongWithWrongRoundId() throws Exception {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator).buildNoPlayers();
        Player player = RoomMother.generatePlayer();
        Song song = SongMother.songBuilder().build();

        var players = new ArrayList<>(mockRoom.getPlayers());
        players.add(player);
        mockRoom.setPlayers(players);

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/submit-song", ROOM_ID.getValue())
                        .param("playerId", player.getPlayerId())
                        .param("roundId", "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(song.toJson()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Round is not current"));
    }

    @Test
    @DisplayName("select a song successfully")
    void selectSongSuccessfully() throws Exception {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator).buildNoPlayers();
        Player player = RoomMother.generatePlayer();
        Song song = SongMother.songBuilder().build();

        var players = new ArrayList<>(mockRoom.getPlayers());
        players.add(player);
        mockRoom.setPlayers(players);

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));

        var updatedRoom = Room.builder()
                .game(mockRoom.getGame())
                .creator(mockRoom.getCreator())
                .roomId(mockRoom.getRoomId())
                .players(mockRoom.getPlayers())
                .rounds(generateRounds(creator))
                .build();

        List<Map<String, Song>> songs = updatedRoom.getRounds().getFirst().getSongSuggestions() == null ?
                new ArrayList<>() : updatedRoom.getRounds().getFirst().getSongSuggestions();
        songs.add(Collections.singletonMap(player.getPlayerId(), song));
        updatedRoom.getRounds().getFirst().setSongSuggestions(songs);
        updatedRoom.getPlayers().get(1).setScore(1);
        updatedRoom.getRounds().getFirst().setWinningSong(songs.getFirst());
        updatedRoom.getRounds().get(1).setCurrentBoss(player);

        when(service.selectSong(any(Room.class), any(Integer.class), any(String.class)))
                .thenReturn(Optional.of(updatedRoom));

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/select-song", ROOM_ID.getValue())
                        .param("currentBossId", creator.getPlayerId())
                        .param("playerId", player.getPlayerId())
                        .param("roundId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId.value").value(ROOM_ID.getValue()))
                .andExpect(jsonPath("$.rounds[0].winningSong." + player.getPlayerId() + ".title").value(song.getTitle()))
                .andExpect(jsonPath("$.players[1].score").value(1))
                .andExpect(jsonPath("$.rounds[1].currentBoss.playerId").value(player.getPlayerId()));
    }

    @Test
    @DisplayName("select a song with unknown room id")
    void selectSongWithUnknownRoomId() throws Exception {
        Creator creator = generateCreator();
        Player player = RoomMother.generatePlayer();

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/select-song", ROOM_ID.getValue())
                        .param("currentBossId", creator.getPlayerId())
                        .param("playerId", player.getPlayerId())
                        .param("roundId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("select a song with wrong current boss id")
    void selectSongWithWrongCurrentBossId() throws Exception {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator).buildNoPlayers();
        Player player = RoomMother.generatePlayer();

        var players = new ArrayList<>(mockRoom.getPlayers());
        players.add(player);
        mockRoom.setPlayers(players);

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/select-song", ROOM_ID.getValue())
                        .param("currentBossId", "wrongCurrentBossId")
                        .param("playerId", player.getPlayerId())
                        .param("roundId", "1"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Player is not the current boss"));
    }

    @Test
    @DisplayName("select a song with already selected winning song")
    void selectSongWithAlreadySelectedWinningSong() throws Exception {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator).buildNoPlayers();
        Player player = RoomMother.generatePlayer();
        Song song = SongMother.songBuilder().build();

        var players = new ArrayList<>(mockRoom.getPlayers());
        players.add(player);
        mockRoom.setPlayers(players);
        List<Map<String, Song>> songs = mockRoom.getRounds().getFirst().getSongSuggestions() == null ?
                new ArrayList<>() : mockRoom.getRounds().getFirst().getSongSuggestions();
        songs.add(Collections.singletonMap(player.getPlayerId(), song));
        mockRoom.getRounds().getFirst().setSongSuggestions(songs);
        mockRoom.getPlayers().get(1).setScore(1);
        mockRoom.getRounds().getFirst().setWinningSong(songs.getFirst());

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/select-song", ROOM_ID.getValue())
                        .param("currentBossId", creator.getPlayerId())
                        .param("playerId", player.getPlayerId())
                        .param("roundId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Winning song already selected"));
    }

    @Test
    @DisplayName("select a song with wrong round id")
    void selectSongWithWrongRoundId() throws Exception {
        Creator creator = generateCreator();
        Room mockRoom = roomBuilder(ROOM_ID, creator).buildNoPlayers();
        Player player = RoomMother.generatePlayer();

        var players = new ArrayList<>(mockRoom.getPlayers());
        players.add(player);
        mockRoom.setPlayers(players);

        when(service.findById(argThat(roomId -> roomId.getValue().equals(ROOM_ID.getValue())))).thenReturn(Optional.of(mockRoom));

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/rooms/{roomId}/select-song", ROOM_ID.getValue())
                        .param("currentBossId", creator.getPlayerId())
                        .param("playerId", player.getPlayerId())
                        .param("roundId", "2"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Round is not current"));
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
                Arguments.of(1),
                Arguments.of(2),
                Arguments.of(3)
        );
    }


}
