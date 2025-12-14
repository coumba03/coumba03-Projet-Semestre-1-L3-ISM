package com.brasilburger.repository;

import com.brasilburger.model.Livreur;
import java.util.List;

public interface LivreurRepository {
    boolean creer(Livreur livreur);
    List<Livreur> listerTous();
    Livreur obtenirParId(int id);
    boolean modifier(Livreur livreur);
    boolean archiver(int id);
}
