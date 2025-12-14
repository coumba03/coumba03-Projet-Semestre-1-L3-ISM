package com.brasilburger.service.impl;

import com.brasilburger.repository.MenuRepository;
import com.brasilburger.repository.BurgerRepository;
import com.brasilburger.repository.ComplementRepository;
import com.brasilburger.model.Menu;
import com.brasilburger.service.MenuService;
import java.util.List;

public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuDAO;
    private final BurgerRepository burgerDAO;
    private final ComplementRepository complementDAO;

    public MenuServiceImpl(MenuRepository menuDAO, BurgerRepository burgerDAO, ComplementRepository complementDAO) {
        this.menuDAO = menuDAO;
        this.burgerDAO = burgerDAO;
        this.complementDAO = complementDAO;
    }

    @Override
    public boolean creer(Menu menu, int burgerId, int boissonId, int friteId) {
        return menuDAO.creer(menu, burgerId, boissonId, friteId);
    }

    @Override
    public List<Menu> listerTous() {
        return menuDAO.listerTous();
    }

    @Override
    public Menu obtenirParId(int id) {
        return menuDAO.obtenirParId(id);
    }

    @Override
    public boolean modifier(Menu menu, int burgerId, int boissonId, int friteId) {
        return menuDAO.modifier(menu, burgerId, boissonId, friteId);
    }

    @Override
    public boolean archiver(int id) {
        return menuDAO.archiver(id);
    }
}
