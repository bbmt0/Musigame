package com.masterproject.musigame.adapter.db.rooms;

public class FirebaseRoomDocumentNotSaved extends RuntimeException {
    public FirebaseRoomDocumentNotSaved() {
        super("Problem occurred while saving the room");
    }
}
