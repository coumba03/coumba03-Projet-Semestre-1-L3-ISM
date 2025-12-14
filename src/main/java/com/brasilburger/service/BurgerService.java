package com.brasilburger.service;

import com.brasilburger.model.Burger;
import java.util.List;

public interface BurgerService {
    boolean creer(Burger burger);
    List<Burger> listerTous();
    List<Burger> listerDisponibles();
    Burger obtenirParId(int id);
    boolean modifier(Burger burger);
    boolean archiver(int id);
    boolean supprimer(int id);
}
