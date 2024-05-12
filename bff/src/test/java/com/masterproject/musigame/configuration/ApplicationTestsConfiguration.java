package com.masterproject.musigame.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.masterproject.musigame.configuration.firebase.FirebaseService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@TestConfiguration
class ApplicationTestsConfiguration {
    @Bean
    public FirebaseService firebaseService() throws IOException {
        File file = new File("src/test/resources/firebase_key.json");
        FileInputStream fis = new FileInputStream(file);
        GoogleCredentials credentials = GoogleCredentials.fromStream(fis);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
        Firestore db = FirestoreClient.getFirestore();
        return new FirebaseService();
    }
}
