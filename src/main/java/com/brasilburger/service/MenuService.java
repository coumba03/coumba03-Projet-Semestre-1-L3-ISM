package com.brasilburger.service;

import com.brasilburger.model.Menu;
import java.util.List;

public interface MenuService {
    boolean creer(Menu menu, int burgerId, int boissonId, int friteId);
    List<Menu> listerTous();
    Menu obtenirParId(int id);
    boolean modifier(Menu menu, int burgerId, int boissonId, int friteId);
    boolean archiver(int id);
}
