package com.masterproject.musigame.configuration.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseService {
    FirebaseDatabase db;

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

        Firestore db = FirestoreClient.getFirestore();

    }

    public FirebaseDatabase getDb() {
        return db;
    }
}
