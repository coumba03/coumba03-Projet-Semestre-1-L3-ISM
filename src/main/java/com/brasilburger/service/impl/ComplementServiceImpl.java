package com.brasilburger.service.impl;

import com.brasilburger.repository.ComplementRepository;
import com.brasilburger.model.Complement;
import com.brasilburger.service.ComplementService;
import java.util.List;

public class ComplementServiceImpl implements ComplementService {

    private final ComplementRepository complementDAO;

    public ComplementServiceImpl(ComplementRepository complementDAO) {
        this.complementDAO = complementDAO;
    }

    @Override
    public boolean creer(Complement complement) {
        return complementDAO.creer(complement);
    }

    @Override
    public List<Complement> listerTous() {
        return complementDAO.listerTous();
    }

    @Override
    public List<Complement> listerParType(String type) {
        return complementDAO.listerParType(type);
    }

    @Override
    public Complement obtenirParId(int id) {
        return complementDAO.obtenirParId(id);
    }

    @Override
    public boolean modifier(Complement complement) {
        return complementDAO.modifier(complement);
    }

    @Override
    public boolean archiver(int id) {
        return complementDAO.archiver(id);
    }
}
