package com.brasilburger.service.impl;

import com.brasilburger.model.MenuItem;
import com.brasilburger.repository.MenuItemRepository;
import com.brasilburger.service.MenuItemService;
import java.util.List;

public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository repository;

    public MenuItemServiceImpl(MenuItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean creer(MenuItem item) {
        return repository.creer(item);
    }

    @Override
    public List<MenuItem> listerTous() {
        return repository.listerTous();
    }

    @Override
    public MenuItem obtenirParId(int id) {
        return repository.obtenirParId(id);
    }

    @Override
    public List<MenuItem> listerParMenu(int menuId) {
        return repository.listerParMenu(menuId);
    }

    @Override
    public boolean modifier(MenuItem item) {
        return repository.modifier(item);
    }

    @Override
    public boolean supprimer(int id) {
        return repository.supprimer(id);
    }
}
