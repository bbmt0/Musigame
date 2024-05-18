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
                .score(0)
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
                .score(0)
                .build();
        return new Creator(player.getPlayerId(), player.getUsername(), player.getProfilePictureUrl(), player.getScore());
    }

    public static List<Round> generateRounds(Creator creator, Integer numberOfRounds) {
        List<Round> rounds = new ArrayList<>(numberOfRounds);
        for (int i = 0; i < numberOfRounds; i++) {
            rounds.add(Round.builder().build());
            rounds.get(i).setRoundNumber(i + 1);
        }
        rounds.getFirst().setCurrentBoss(creator);
        return rounds;
    }

    public static String generatePlayerId() {
        return Arbitraries.strings().alpha().ofMinLength(5).ofMaxLength(5).sample();
    }

    public static Room generateRoomWithRoundsAndNumber(Room room, Integer numberOfRounds) {
        return Room.builder()
                .roomId(room.getRoomId())
                .game(room.getGame())
                .creator(room.getCreator())
                .numberOfRound(numberOfRounds)
                .currentRound(1)
                .players(room.getPlayers())
                .rounds(generateRounds(room.getCreator(), numberOfRounds))
                .build();
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
            private Integer numberOfRounds = null;
            private Integer currentRound = 1;
            private List<Player> players = generatePlayersList();
            private List<Round> rounds = null;


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
                return Room.builder().roomId(roomId).game(game).creator(creator).numberOfRound(numberOfRounds).currentRound(currentRound).rounds(rounds).players(players).build();
            }

            public Room buildNoPlayers() {
                return Room.builder().roomId(roomId).game(game).creator(creator).numberOfRound(numberOfRounds).currentRound(currentRound).rounds(rounds).players(Collections.singletonList(creator)).build();
            }

            private List<Player> generatePlayersList() {
                List<Player> allPlayers = Rooms.players().sampleStream().limit(5).collect(Collectors.toList());
                allPlayers.addFirst(creator);
                return allPlayers;
            }


        }


    }
}
