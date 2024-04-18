package com.masterproject.musigame.rooms;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit")
@Tag("domain")
@DisplayName("RoomId should")
class RoomIdTests {
    @Test
    @DisplayName("create RoomId when there is a valid pattern")
    void buildRoomIdWithCorrectPattern() {
        String validRoomIdValue = "AbCd";
        RoomId roomId = RoomId.build(validRoomIdValue);
        assertEquals(validRoomIdValue, roomId.getValue());
    }

}
