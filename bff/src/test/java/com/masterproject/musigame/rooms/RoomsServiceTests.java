package com.masterproject.musigame.rooms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.masterproject.musigame.rooms.RoomMother.Rooms.ids;
import static com.masterproject.musigame.rooms.RoomMother.generateCreator;
import static com.masterproject.musigame.rooms.RoomMother.roomBuilder;
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
    @DisplayName("start a game with known RoomId")
    void startGameWithKnownRoomId(GameType gameType) {
        Creator creator = generateCreator();
        var room = service.save(creator);

        var actual = service.startGame(room, creator, gameType);
        assertThat(actual).isPresent();
    }

    @ParameterizedTest
    @MethodSource("gameTypeProvider")
    @DisplayName("start a game with unknown RoomId")
    void startGameWithUnknownRoomId(GameType gameType) {
        Creator creator = generateCreator();
        service.save(creator);

        var actual = service.startGame(roomBuilder().build(), creator, gameType);
        assertThat(actual).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("gameTypeProvider")
    @DisplayName("start a game with unknown creator")
    void startGameWithUnknownCreator(GameType gameType) {
        Creator creator = generateCreator();
        var room = service.save(creator);

        var actual = service.startGame(room, generateCreator(), gameType);
        assertThat(actual).isEmpty();
    }

    static Stream<Arguments> gameTypeProvider() {
        return Stream.of(
                Arguments.of(GameType.IMPOSTER)
        );
    }

}
