package fr.iutlan.rendezvous;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseConfig {
    @SuppressWarnings("deprecation")
    public static void initFirebase() throws IOException {
        System.out.println("Initializing Firebase...");
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/google-services.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

        System.out.println("Firebase initialized successfully.");
    }

}
