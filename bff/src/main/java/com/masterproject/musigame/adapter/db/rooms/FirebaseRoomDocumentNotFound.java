package com.masterproject.musigame.adapter.db.rooms;

public class FirebaseRoomDocumentNotFound extends RuntimeException {
    public FirebaseRoomDocumentNotFound() {
        super("Room document not found");
    }
}
