package fr.iutlan.rendezvous.ws;

import jakarta.jws.WebService;

@WebService(targetNamespace = "http://ws.rendezvous.iutlan.fr/")
public interface Auth {
    String updateUserRoleToDoctor(String keyCode, String email, String profession);

    String registerDoctor(String keyCode, String name, String phone, String email, String password, String profession);

    String changeRoleToPatient(String keyCode, String email);

    String getAllDoctors(String keyCode);
}
