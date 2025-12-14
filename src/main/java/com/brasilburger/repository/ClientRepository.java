package com.brasilburger.repository;

import com.brasilburger.model.Client;
import java.util.List;

public interface ClientRepository {
    boolean creer(Client client);
    List<Client> listerTous();
    Client obtenirParId(int id);
    Client obtenirParEmail(String email);
    boolean modifier(Client client);
    boolean archiver(int id);
}
