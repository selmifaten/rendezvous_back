package fr.iutlan.rendezvous.ws;

import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;

@WebService(targetNamespace = "http://ws.rendezvous.iutlan.fr/")
public interface Auth {
    String updateUserRoleToDoctor(String keyCode, String email, String profession);

    String registerDoctor(String keyCode, String name, String phone, String email, String password, String profession);
}
