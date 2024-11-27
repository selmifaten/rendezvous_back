package fr.iutlan.rendezvous;

import fr.iutlan.rendezvous.ws.AuthImp;
import jakarta.xml.ws.Endpoint;

public class App {
    public static void main(String[] args) {
        try {
            FirebaseConfig.initFirebase(); // Initialize Firebase

            AuthImp authService = new AuthImp();
            String url = "http://localhost:5000/ws/auth";

            Endpoint.publish(url, authService);
            System.out.println("Service running at: " + url);
        } catch (Exception e) {
            System.out.println("Error starting service: " + e.getMessage());
        }
    }
}
