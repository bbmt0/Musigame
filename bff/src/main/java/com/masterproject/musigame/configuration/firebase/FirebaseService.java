package com.masterproject.musigame.configuration.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FirebaseService {
    Firestore db;

    public FirebaseService() throws IOException {
        InputStream serviceAccount;

        try {
            serviceAccount = new ClassPathResource("configuration/firebase_key.json").getInputStream();
        } catch (IOException e) {
            String firebaseKey = System.getenv("FIREBASE_KEY");
            if (firebaseKey == null) {
                throw new IOException("Firebase key not found in environment variables or resources");
            }
            serviceAccount = new ByteArrayInputStream(firebaseKey.getBytes());
        }
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
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
