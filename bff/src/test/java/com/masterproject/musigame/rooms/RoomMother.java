package com.masterproject.musigame.rooms;

import lombok.Setter;
import lombok.experimental.Accessors;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RoomMother {
    public static Player generatePlayer() {
        Arbitrary<String> usernameArbitrary = Arbitraries.strings().alpha().ofMinLength(5).ofMaxLength(15);
        Arbitrary<String> profilePictureUrlArbitrary = Arbitraries.strings().alpha().ofMinLength(10).ofMaxLength(50);

        return Player.builder()
                .username(usernameArbitrary.sample())
                .profilePictureUrl(profilePictureUrlArbitrary.sample())
                .build();
    }

    public static Creator generateCreator() {
        Arbitrary<String> usernameArbitrary = Arbitraries.strings().alpha().ofMinLength(5).ofMaxLength(15);
        Arbitrary<String> profilePictureUrlArbitrary = Arbitraries.strings().alpha().ofMinLength(10).ofMaxLength(50);

        Player player = Player.builder()
                .username(usernameArbitrary.sample())
                .profilePictureUrl(profilePictureUrlArbitrary.sample())
                .build();
        return new Creator(player.getUsername(), player.getProfilePictureUrl());
    }

    public static Rooms.Builder roomBuilder() {
        return new Rooms.Builder();
    }

    public static Rooms.Builder roomBuilder(RoomId roomId) {
        return new Rooms.Builder(roomId);
    }

    public static Rooms.Builder roomBuilder(RoomId roomId, Creator creator) {
        return new Rooms.Builder(roomId, creator);
    }

    public static class Rooms {
        public static Arbitrary<RoomId> ids() {
            return Arbitraries.create(RoomId::generateId);
        }

        public static Arbitrary<Player> players() {
            return Arbitraries.create(RoomMother::generatePlayer);
        }

        @Setter
        @Accessors(fluent = true)
        public static class Builder {
            private RoomId roomId;
            private Game game = Game.builder().isGameLaunched(false).gameType(GameType.IMPOSTER).build();
            private Creator creator = generateCreator();
            private List<Player> players = generatePlayersList();

            Builder() {
                this.roomId = ids().sample();
            }

            Builder(RoomId roomId) {
                this.roomId = roomId;
            }

            Builder(RoomId roomId, Creator creator) {
                this.roomId = roomId;
                this.creator = creator;
            }

            public Room build() {
                return Room.builder().roomId(roomId).game(game).creator(creator).players(players).build();
            }

            public Room buildNoPlayers() {
                return Room.builder().roomId(roomId).game(game).creator(creator).players(Collections.singletonList(creator)).build();
            }

            private List<Player> generatePlayersList() {
                List<Player> allPlayers = Rooms.players().sampleStream().limit(5).collect(Collectors.toList());
                allPlayers.addFirst(creator);
                return allPlayers;
            }

        }


    }
}