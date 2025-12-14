package com.brasilburger.service.impl;

import com.brasilburger.model.Gestionnaire;
import com.brasilburger.repository.GestionnaireRepository;
import com.brasilburger.service.GestionnaireService;
import java.util.List;

public class GestionnaireServiceImpl implements GestionnaireService {
    private final GestionnaireRepository repository;

    public GestionnaireServiceImpl(GestionnaireRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean creer(Gestionnaire gestionnaire) {
        return repository.creer(gestionnaire);
    }

    @Override
    public List<Gestionnaire> listerTous() {
        return repository.listerTous();
    }

    @Override
    public Gestionnaire obtenirParId(int id) {
        return repository.obtenirParId(id);
    }

    @Override
    public Gestionnaire obtenirParEmail(String email) {
        return repository.obtenirParEmail(email);
    }

    @Override
    public boolean modifier(Gestionnaire gestionnaire) {
        return repository.modifier(gestionnaire);
    }

    @Override
    public boolean archiver(int id) {
        return repository.archiver(id);
    }
}
