package com.masterproject.musigame.rooms;

import lombok.Setter;
import lombok.experimental.Accessors;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RoomMother {
    public static Player generatePlayer() {
        Arbitrary<String> usernameArbitrary = Arbitraries.strings().alpha().ofMinLength(5).ofMaxLength(15);
        Arbitrary<String> profilePictureUrlArbitrary = Arbitraries.strings().alpha().ofMinLength(10).ofMaxLength(50);
        Arbitrary<String> playerIdArbitrary = Arbitraries.create(RoomMother::generatePlayerId);

        return Player.builder()
                .playerId(playerIdArbitrary.sample())
                .username(usernameArbitrary.sample())
                .profilePictureUrl(profilePictureUrlArbitrary.sample())
                .build();
    }

    public static Creator generateCreator() {
        Arbitrary<String> usernameArbitrary = Arbitraries.strings().alpha().ofMinLength(5).ofMaxLength(15);
        Arbitrary<String> profilePictureUrlArbitrary = Arbitraries.strings().alpha().ofMinLength(10).ofMaxLength(50);
        Arbitrary<String> playerIdArbitrary = Arbitraries.create(RoomMother::generatePlayerId);

        Player player = Player.builder()
                .playerId(playerIdArbitrary.sample())
                .username(usernameArbitrary.sample())
                .profilePictureUrl(profilePictureUrlArbitrary.sample())
                .build();
        return new Creator(player.getPlayerId(), player.getUsername(), player.getProfilePictureUrl());
    }

    public static List<Round> generateRounds(Creator creator) {
        List<Round> rounds = new ArrayList<>(3);
        rounds.add(Round.builder().build());
        rounds.add(Round.builder().build());
        rounds.add(Round.builder().build());
        rounds.getFirst().setCurrentBoss(creator);
        rounds.getFirst().setRoundNumber(1);
        rounds.get(1).setRoundNumber(2);
        rounds.getLast().setRoundNumber(3);
        return rounds;
    }

    public static String generatePlayerId() {
        return Arbitraries.strings().alpha().ofMinLength(5).ofMaxLength(5).sample();
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

    public static Rooms.Builder roomBuilder(RoomId roomId, Creator creator, GameType gameType) {
        return new Rooms.Builder(roomId, creator, gameType);
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
            private Game game = Game.builder().isGameLaunched(false).gameType(null).build();
            private Creator creator = generateCreator();
            private List<Player> players = generatePlayersList();
            private List<Round> rounds;


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

            Builder(RoomId roomId, Creator creator, GameType gameType) {
                this.roomId = roomId;
                this.creator = creator;
                this.game = Game.builder().isGameLaunched(false).gameType(gameType).build();
            }

            public Room build() {
                this.rounds = generateRounds(creator);
                return Room.builder().roomId(roomId).game(game).creator(creator).players(players).rounds(rounds).build();
            }

            public Room buildNoPlayers() {
                this.rounds = generateRounds(creator);
                return Room.builder().roomId(roomId).game(game).creator(creator).players(Collections.singletonList(creator)).rounds(rounds).build();
            }

            private List<Player> generatePlayersList() {
                List<Player> allPlayers = Rooms.players().sampleStream().limit(5).collect(Collectors.toList());
                allPlayers.addFirst(creator);
                return allPlayers;
            }


        }


    }
}
