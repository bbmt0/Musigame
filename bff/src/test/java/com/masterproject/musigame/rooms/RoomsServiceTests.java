package com.masterproject.musigame.rooms;

import com.masterproject.musigame.songs.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.masterproject.musigame.rooms.RoomMother.Rooms.ids;
import static com.masterproject.musigame.rooms.RoomMother.*;
import static com.masterproject.musigame.songs.SongMother.songBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@Tag("service")
@DisplayName("Rooms service should")
class RoomsServiceTests {
    private RoomsService service;
    private InMemoryRoomsRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRoomsRepository();
        service = new RoomsService(repository);
    }

    @Test
    @DisplayName("create a room successfully")
    void createRoom() {
        Room room = roomBuilder().buildNoPlayers();
        var actual = service.save(room.getCreator());

        assertThat(actual).usingRecursiveComparison().ignoringFields("roomId.value").isEqualTo(room);
    }

    @Test
    @DisplayName("get a room with known RoomId")
    void getRoomWithKnownRoomId() {
        Creator creator = generateCreator();
        var expected = service.save(creator);

        var actual = service.findById(expected.getRoomId());
        assertThat(actual).contains(expected);
    }

    @Test
    @DisplayName("get null with unknown RoomId")
    void getNullWithUnknownRoomId() {
        Creator creator = generateCreator();
        service.save(creator);

        var actual = service.findById(ids().sample());
        assertThat(actual).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("gameTypeProvider")
    @DisplayName("start a game ")
    void startGame(GameType gameType) {
        Creator creator = generateCreator();
        var room = service.save(creator);
        room.getGame().setGameType(gameType);

        var actual = service.startGame(room, gameType);
        assertThat(actual.get()).usingRecursiveComparison().ignoringFields("roomId.value").isEqualTo(room);
    }

    @Test
    @DisplayName("submit a sentence")
    void submitSentence() {
        Creator creator = generateCreator();
        var room = service.save(creator);
        room.getRounds().getFirst().setSentence("sentence");


        var actual = service.submitSentence(room, 1, "sentence");
        assertThat(actual.get()).usingRecursiveComparison().ignoringFields("roomId.value").isEqualTo(room);
    }

    @Test
    @DisplayName("join a room")
    void joinRoom() {
        Creator creator = generateCreator();
        var room = service.save(creator);
        Player player = generatePlayer();

        var actual = service.join(room, player);
        assertThat(actual.get()).usingRecursiveComparison().ignoringFields("roomId.value").isEqualTo(room);
    }

    @Test
    @DisplayName("submit a song")
    void submitSong() {
        Creator creator = generateCreator();
        var room = service.save(creator);
        Song song = songBuilder().build();
        Player player = generatePlayer();
        room.getRounds().getFirst().setCurrentBoss(player);

        var actual = service.submitSong(room, 1, player.getPlayerId(), song);
        assertThat(actual.get()).usingRecursiveComparison().ignoringFields("roomId.value").isEqualTo(room);
    }

    @Test
    @DisplayName("select a song")
    void selectSong() {
        Creator creator = generateCreator();
        var room = service.save(creator);
        Song song = songBuilder().build();
        Player player = generatePlayer();
        var players = new ArrayList<>(room.getPlayers());
        players.add(player);
        room.setPlayers(players);
        room.getRounds().getFirst().setCurrentBoss(creator);
        room.getRounds().getFirst().setSongSuggestions(List.of(Map.of(player.getPlayerId(), song)));

        var actual = service.selectSong(room, 1, player.getPlayerId());
        room.getRounds().getFirst().setWinningSong(Map.of(player.getPlayerId(), song));
        room.getRounds().get(1).setCurrentBoss(player);
        room.getPlayers().stream().filter(p -> p.getPlayerId().equals(player.getPlayerId())).findFirst().get().setScore(player.getScore() + 1);

        assertThat(actual.get()).usingRecursiveComparison().ignoringFields("roomId.value").isEqualTo(room);
    }

    @Test
    @DisplayName("start next round")
    void startNextRound() {
        Creator creator = generateCreator();
        var room = service.save(creator);
        room.getRounds().getFirst().setCurrentBoss(creator);
        room.getRounds().getFirst().setWinningSong(Map.of(creator.getPlayerId(), songBuilder().build()));

        var actual = service.startNextRound(room);
        room.getRounds().get(1).setCurrentBoss(creator);

        assertThat(actual.get()).usingRecursiveComparison().ignoringFields("roomId.value").isEqualTo(room);
    }


    static Stream<Arguments> gameTypeProvider() {
        return Stream.of(
                Arguments.of(GameType.BOSS_SELECTION)
        );
    }

}
