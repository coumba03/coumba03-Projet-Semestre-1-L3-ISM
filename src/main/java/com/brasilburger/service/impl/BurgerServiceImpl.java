package com.brasilburger.service.impl;

import com.brasilburger.repository.BurgerRepository;
import com.brasilburger.model.Burger;
import com.brasilburger.service.BurgerService;
import java.util.List;

public class BurgerServiceImpl implements BurgerService {

    private final BurgerRepository burgerDAO;

    public BurgerServiceImpl(BurgerRepository burgerDAO) {
        this.burgerDAO = burgerDAO;
    }

    @Override
    public boolean creer(Burger burger) {
        return burgerDAO.creer(burger);
    }

    @Override
    public List<Burger> listerTous() {
        return burgerDAO.listerTous();
    }

    @Override
    public List<Burger> listerDisponibles() {
        return burgerDAO.listerDisponibles();
    }

    @Override
    public Burger obtenirParId(int id) {
        return burgerDAO.obtenirParId(id);
    }

    @Override
    public boolean modifier(Burger burger) {
        return burgerDAO.modifier(burger);
    }

    @Override
    public boolean archiver(int id) {
        return burgerDAO.archiver(id);
    }

    @Override
    public boolean supprimer(int id) {
        return burgerDAO.supprimer(id);
    }
}
