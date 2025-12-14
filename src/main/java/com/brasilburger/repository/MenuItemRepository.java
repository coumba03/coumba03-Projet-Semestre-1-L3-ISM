package com.brasilburger.repository;

import com.brasilburger.model.MenuItem;
import java.util.List;

public interface MenuItemRepository {
    boolean creer(MenuItem item);
    List<MenuItem> listerTous();
    MenuItem obtenirParId(int id);
    List<MenuItem> listerParMenu(int menuId);
    boolean modifier(MenuItem item);
    boolean supprimer(int id);
}
