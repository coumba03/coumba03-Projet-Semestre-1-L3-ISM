package com.brasilburger.service.impl;

import com.brasilburger.model.Paiement;
import com.brasilburger.repository.PaiementRepository;
import com.brasilburger.service.PaiementService;
import java.util.List;

public class PaiementServiceImpl implements PaiementService {
    private final PaiementRepository repository;

    public PaiementServiceImpl(PaiementRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean creer(Paiement paiement) {
        return repository.creer(paiement);
    }

    @Override
    public List<Paiement> listerTous() {
        return repository.listerTous();
    }

    @Override
    public Paiement obtenirParId(int id) {
        return repository.obtenirParId(id);
    }

    @Override
    public Paiement obtenirParCommande(int commandeId) {
        return repository.obtenirParCommande(commandeId);
    }

    @Override
    public List<Paiement> listerParClient(int clientId) {
        return repository.listerParClient(clientId);
    }

    @Override
    public boolean modifier(Paiement paiement) {
        return repository.modifier(paiement);
    }
}
