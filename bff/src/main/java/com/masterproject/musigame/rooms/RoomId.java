package com.masterproject.musigame.rooms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

@Getter
@RequiredArgsConstructor
public class RoomId {
    public static final String ROOMID_PATTERN = "^[a-zA-Z0-9]{5}$";
    private final String value;

    public static RoomId build(String roomIdValue) {
        if (!isValidRoomId(roomIdValue)) throw new InvalidRoomIdException();
        return new RoomId(roomIdValue);
    }

    private static boolean isValidRoomId(String roomIdValue) {
        Pattern roomIdPattern = Pattern.compile(ROOMID_PATTERN);
        return roomIdPattern.matcher(roomIdValue).matches();
    }

}
