package fr.iutlan.rendezvous.ws;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.util.concurrent.ExecutionException;

@WebService(endpointInterface = "fr.iutlan.rendezvous.ws.Auth", targetNamespace = "http://ws.rendezvous.iutlan.fr/")
public class AuthImp implements Auth {
    private final Firestore db = FirestoreClient.getFirestore();

    @WebMethod
    public String login(String username, String password) {
        CollectionReference users = db.collection("User");
        try {
            ApiFuture<DocumentSnapshot> future = users.document(username).get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                String storedPassword = document.getString("password");
                if (storedPassword != null && storedPassword.equals(password)) {
                    return "Login successful! Welcome " + username;
                } else {
                    return "Invalid password.";
                }
            } else {
                return "User does not exist.";
            }
        } catch (InterruptedException | ExecutionException e) {
            return "Error during login: " + e.getMessage();
        }
    }

    @WebMethod
    public String register(String username, String password) {
        System.out.println(username+password);
        if (username == null || username.trim().isEmpty()) {
            return "Registration failed: Username must not be empty.";
        }
        CollectionReference users = db.collection("User");
        try {
            ApiFuture<DocumentSnapshot> future = users.document(username).get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                return "Registration failed: Username already exists.";
            } else {
                users.document(username).set(new User(username, password));
                return "Registration successful! Welcome " + username;
            }
        } catch (Exception e) {
            return "Error during registration: " + e.getMessage();
        }
    }

    private static class User {
        public String username;
        public String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}

