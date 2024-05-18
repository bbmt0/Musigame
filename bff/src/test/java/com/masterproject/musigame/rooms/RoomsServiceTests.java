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
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    @DisplayName("throws exception when room is null while saving")
    void throwExceptionWhenRoomIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.save(null));
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
    @DisplayName("throws exception when RoomId is null while getting room")
    void throwExceptionWhenRoomIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.findById(null));
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

        var actual = service.startGame(room, gameType, 3);
        assertThat(actual.get()).usingRecursiveComparison().ignoringFields("roomId.value").isEqualTo(room);
    }


    @Test
    @DisplayName("throws exception when room is null while starting game")
    void throwExceptionWhenRoomIsNullWhileStartingGame() {
        assertThrows(IllegalArgumentException.class, () -> service.startGame(null, GameType.BOSS_SELECTION,3));
    }

    @Test
    @DisplayName("submit a sentence")
    void submitSentence() {
        Creator creator = generateCreator();
        var room = service.save(creator);
        room = generateRoomWithRoundsAndNumber(room, 3);
        room.getRounds().getFirst().setSentence("sentence");


        var actual = service.submitSentence(room, 1, "sentence");
        assertThat(actual.get()).usingRecursiveComparison().ignoringFields("roomId.value").isEqualTo(room);
    }

    @Test
    @DisplayName("throws exception when sentence is null while submitting sentence")
    void throwExceptionWhenSentenceIsNull() {
        Creator creator = generateCreator();
        var room = service.save(creator);

        assertThrows(IllegalArgumentException.class, () -> service.submitSentence(room, 1, null));
    }

    @Test
    @DisplayName("throws exception when room is null while submitting sentence")
    void throwExceptionWhenRoomIsNullWhileSubmittingSentence() {
        assertThrows(IllegalArgumentException.class, () -> service.submitSentence(null, 1, "sentence"));
    }

    @Test
    @DisplayName("throws exception when round number null while submitting sentence")
    void throwExceptionWhenRoundNumberIsNullWhileSubmittingSentence() {
        Creator creator = generateCreator();
        var room = service.save(creator);

        assertThrows(IllegalArgumentException.class, () -> service.submitSentence(room, null, "sentence"));
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
    @DisplayName("throws exception when player is null while joining room")
    void throwExceptionWhenPlayerIsNullWhileJoiningRoom() {
        Creator creator = generateCreator();
        var room = service.save(creator);

        assertThrows(IllegalArgumentException.class, () -> service.join(room, null));
    }

    @Test
    @DisplayName("throws exception when room is null while joining room")
    void throwExceptionWhenRoomIsNullWhileJoiningRoom() {
        Player player = generatePlayer();

        assertThrows(IllegalArgumentException.class, () -> service.join(null, player));
    }

    @Test
    @DisplayName("submit a song")
    void submitSong() {
        Creator creator = generateCreator();
        var room = service.save(creator);
        room = generateRoomWithRoundsAndNumber(room, 3);
        Song song = songBuilder().build();
        Player player = generatePlayer();
        room.getRounds().getFirst().setCurrentBoss(player);

        var actual = service.submitSong(room, 1, player.getPlayerId(), song);
        assertThat(actual.get()).usingRecursiveComparison().ignoringFields("roomId.value").isEqualTo(room);
    }

    @Test
    @DisplayName("throws exception when song is null while submitting song")
    void throwExceptionWhenSongIsNullWhileSubmittingSong() {
        Creator creator = generateCreator();
        var room = service.save(creator);
        Player player = generatePlayer();

        assertThrows(IllegalArgumentException.class, () -> service.submitSong(room, 1, player.getPlayerId(), null));
    }

    @Test
    @DisplayName("throws exception when player is null while submitting song")
    void throwExceptionWhenPlayerIsNullWhileSubmittingSong() {
        Creator creator = generateCreator();
        var room = service.save(creator);
        Song song = songBuilder().build();

        assertThrows(IllegalArgumentException.class, () -> service.submitSong(room, 1, null, song));
    }

    @Test
    @DisplayName("throws exception when round number is null while submitting song")
    void throwExceptionWhenRoundNumberIsNullWhileSubmittingSong() {
        Creator creator = generateCreator();
        var room = service.save(creator);
        Song song = songBuilder().build();
        Player player = generatePlayer();

        assertThrows(IllegalArgumentException.class, () -> service.submitSong(room, null, player.getPlayerId(), song));
    }


    @Test
    @DisplayName("select a song")
    void selectSong() {
        Creator creator = generateCreator();
        var room = service.save(creator);
        room = generateRoomWithRoundsAndNumber(room, 3);
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
    @DisplayName("throws exception when player is null while selecting song")
    void throwExceptionWhenPlayerIsNullWhileSelectingSong() {
        Creator creator = generateCreator();
        var room = service.save(creator);

        assertThrows(IllegalArgumentException.class, () -> service.selectSong(room, 1, null));
    }

    @Test
    @DisplayName("throws exception when round number is null while selecting song")
    void throwExceptionWhenRoundNumberIsNullWhileSelectingSong() {
        Creator creator = generateCreator();
        var room = service.save(creator);
        Player player = generatePlayer();

        assertThrows(IllegalArgumentException.class, () -> service.selectSong(room, null, player.getPlayerId()));
    }

    @Test
    @DisplayName("throws exception when room is null while selecting song")
    void throwExceptionWhenRoomIsNullWhileSelectingSong() {
        Player player = generatePlayer();

        assertThrows(IllegalArgumentException.class, () -> service.selectSong(null, 1, player.getPlayerId()));
    }

    @Test
    @DisplayName("start next round")
    void startNextRound() {
        Creator creator = generateCreator();
        var room = service.save(creator);
        room = generateRoomWithRoundsAndNumber(room, 3);
        room.getRounds().getFirst().setCurrentBoss(creator);
        room.getRounds().getFirst().setWinningSong(Map.of(creator.getPlayerId(), songBuilder().build()));

        var actual = service.startNextRound(room);
        room.getRounds().get(1).setCurrentBoss(creator);

        assertThat(actual.get()).usingRecursiveComparison().ignoringFields("roomId.value").isEqualTo(room);
    }

    @Test
    @DisplayName("throws exception when room is null while starting next round")
    void throwExceptionWhenRoomIsNullWhileStartingNextRound() {
        assertThrows(IllegalArgumentException.class, () -> service.startNextRound(null));
    }


    static Stream<Arguments> gameTypeProvider() {
        return Stream.of(
                Arguments.of(GameType.BOSS_SELECTION)
        );
    }

}
