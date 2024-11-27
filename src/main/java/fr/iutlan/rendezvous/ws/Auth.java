package fr.iutlan.rendezvous.ws;

import jakarta.jws.WebService;

@WebService(targetNamespace = "http://ws.rendezvous.iutlan.fr/")
public interface Auth {
    String login(String username, String password);

    String register(String username, String password);
}
