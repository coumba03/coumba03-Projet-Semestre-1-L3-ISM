package com.brasilburger.service.impl;

import com.brasilburger.model.Livreur;
import com.brasilburger.repository.LivreurRepository;
import com.brasilburger.service.LivreurService;
import java.util.List;

public class LivreurServiceImpl implements LivreurService {
    private final LivreurRepository repository;

    public LivreurServiceImpl(LivreurRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean creer(Livreur livreur) {
        return repository.creer(livreur);
    }

    @Override
    public List<Livreur> listerTous() {
        return repository.listerTous();
    }

    @Override
    public Livreur obtenirParId(int id) {
        return repository.obtenirParId(id);
    }

    @Override
    public boolean modifier(Livreur livreur) {
        return repository.modifier(livreur);
    }

    @Override
    public boolean archiver(int id) {
        return repository.archiver(id);
    }
}
