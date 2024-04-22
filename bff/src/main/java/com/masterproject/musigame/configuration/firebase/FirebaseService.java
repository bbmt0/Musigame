package com.masterproject.musigame.configuration.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseService {
    Firestore db;

    public FirebaseService() throws IOException {
        File file = new File(
                getClass().getClassLoader().getResource("configuration/firebase_key.json").getFile()
        );
        FileInputStream fis = new FileInputStream(file);
        GoogleCredentials credentials = GoogleCredentials.fromStream(fis);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);

        db = FirestoreClient.getFirestore();
    }

    public Firestore getDb() {
        return db;
    }
}
