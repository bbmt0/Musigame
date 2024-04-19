package com.masterproject.musigame.configuration.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FirebaseService {
    private final Firestore db;

    public FirebaseService() throws IOException {
        this(FirestoreOptions.getDefaultInstance().getService());
    }

    // Constructor for injecting Firestore instance (for tests)
    public FirebaseService(Firestore firestore) {
        db = firestore;
    }

    public Firestore getDb() {
        return db;
    }
}

