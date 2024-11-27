package fr.iutlan.rendezvous;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseConfig {
    public static void initFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/google-services.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://appointments-1127c.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
    }
}
