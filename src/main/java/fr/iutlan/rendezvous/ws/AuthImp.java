package fr.iutlan.rendezvous.ws;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;

@WebService(endpointInterface = "fr.iutlan.rendezvous.ws.Auth", targetNamespace = "http://ws.rendezvous.iutlan.fr/")
public class AuthImp implements Auth {
    private final Firestore db = FirestoreClient.getFirestore();

    @WebMethod
    public String updateUserRoleToDoctor(@XmlElement(name = "keyCode") String keyCode,
            @XmlElement(name = "email") String email,
            @XmlElement(name = "profession") String profession) {
        System.out.println("updateUserRoleToDoctor() called. Parameters: keyCode=" + keyCode +
                ", email=" + email + ", profession=" + profession);

        String secretKey = "yourSecretKey123";
        if (keyCode == null || !keyCode.equals(secretKey)) {
            return "Access denied: Invalid key code.";
        }

        if (email == null || email.isEmpty() || profession == null || profession.isEmpty()) {
            return "Error: Email and profession cannot be empty.";
        }

        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            UserRecord userRecord = auth.getUserByEmail(email);

            if (userRecord == null) {
                return "Error: User with email " + email + " does not exist.";
            }

            CollectionReference users = db.collection("User");
            String userId = userRecord.getUid();
            users.document(userId).update("role", "doctor");

            CollectionReference doctors = db.collection("Doctor");
            doctors.document(userId).set(new Doctor(userId, profession));

            return "Role updated successfully! User " + email + " is now a doctor.";
        } catch (FirebaseAuthException e) {
            return "Error retrieving user: " + e.getMessage();
        } catch (Exception e) {
            return "Error updating role: " + e.getMessage();
        }
    }

    @WebMethod
    public String registerDoctor(@XmlElement(name = "keyCode") String keyCode,
            @XmlElement(name = "name") String name,
            @XmlElement(name = "phone") String phone,
            @XmlElement(name = "email") String email,
            @XmlElement(name = "password") String password,
            @XmlElement(name = "profession") String profession) {

        System.out.println("registerDoctor() called. Parameters: name=" + name + ", phone=" + phone + ", email=" + email
                + ", password=" + password + ", profession=" + profession);

        String secretKey = "yourSecretKey123";
        if (keyCode == null || !keyCode.equals(secretKey)) {
            return "Access denied: Invalid key code.";
        }

        if (name == null || name.isEmpty() ||
                phone == null || phone.isEmpty() ||
                email == null || email.isEmpty() ||
                password == null || password.isEmpty() ||
                profession == null || profession.isEmpty()) {
            return "Registration failed: Name, Phone, Email, Password and profession cannot be empty.";
        }

        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            UserRecord user = auth.createUser(new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password));

            String userId = user.getUid();

            CollectionReference users = db.collection("User");
            users.document(userId).set(new User(name, phone, email, "doctor"));

            CollectionReference doctors = db.collection("Doctor");
            doctors.document(userId).set(new Doctor(userId, profession));

            return "Doctor registration successful! Welcome Dr. " + name;
        } catch (FirebaseAuthException e) {
            return "Registration failed: " + e.getMessage();
        }
    }

    private static class User {
        @SuppressWarnings("unused")
        public String name;
        @SuppressWarnings("unused")
        public String phone;
        @SuppressWarnings("unused")
        public String email;
        @SuppressWarnings("unused")
        public String role;

        public User(String name, String phone, String email, String role) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.role = role;
        }
    }

    private static class Doctor {
        @SuppressWarnings("unused")
        public String userID;
        @SuppressWarnings("unused")
        public String profession;

        public Doctor(String userID, String profession) {
            this.userID = userID;
            this.profession = profession;
        }
    }

}
