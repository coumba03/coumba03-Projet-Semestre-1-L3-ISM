package com.brasilburger.service;

import com.brasilburger.model.Client;
import java.util.List;

public interface ClientService {
    boolean creer(Client client);
    List<Client> listerTous();
    Client obtenirParId(int id);
    Client obtenirParEmail(String email);
    boolean modifier(Client client);
    boolean archiver(int id);
}
