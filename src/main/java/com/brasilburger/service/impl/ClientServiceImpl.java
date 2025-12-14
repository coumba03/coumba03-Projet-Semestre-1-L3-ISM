package com.brasilburger.service.impl;

import com.brasilburger.model.Client;
import com.brasilburger.repository.ClientRepository;
import com.brasilburger.service.ClientService;
import java.util.List;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean creer(Client client) {
        return repository.creer(client);
    }

    @Override
    public List<Client> listerTous() {
        return repository.listerTous();
    }

    @Override
    public Client obtenirParId(int id) {
        return repository.obtenirParId(id);
    }

    @Override
    public Client obtenirParEmail(String email) {
        return repository.obtenirParEmail(email);
    }

    @Override
    public boolean modifier(Client client) {
        return repository.modifier(client);
    }

    @Override
    public boolean archiver(int id) {
        return repository.archiver(id);
    }
}
