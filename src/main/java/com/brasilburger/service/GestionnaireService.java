package com.brasilburger.service;

import com.brasilburger.model.Gestionnaire;
import java.util.List;

public interface GestionnaireService {
    boolean creer(Gestionnaire gestionnaire);
    List<Gestionnaire> listerTous();
    Gestionnaire obtenirParId(int id);
    Gestionnaire obtenirParEmail(String email);
    boolean modifier(Gestionnaire gestionnaire);
    boolean archiver(int id);
}
