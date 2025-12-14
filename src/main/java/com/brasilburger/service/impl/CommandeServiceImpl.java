package com.brasilburger.service.impl;

import com.brasilburger.model.Commande;
import com.brasilburger.repository.CommandeRepository;
import com.brasilburger.service.CommandeService;
import java.util.List;

public class CommandeServiceImpl implements CommandeService {
    private final CommandeRepository repository;

    public CommandeServiceImpl(CommandeRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean creer(Commande commande) {
        return repository.creer(commande);
    }

    @Override
    public List<Commande> listerTous() {
        return repository.listerTous();
    }

    @Override
    public Commande obtenirParId(int id) {
        return repository.obtenirParId(id);
    }

    @Override
    public List<Commande> listerParClient(int clientId) {
        return repository.listerParClient(clientId);
    }

    @Override
    public boolean modifier(Commande commande) {
        return repository.modifier(commande);
    }

    @Override
    public boolean archiver(int id) {
        return repository.archiver(id);
    }
}
