package com.brasilburger.service;

import com.brasilburger.model.Commande;
import java.util.List;

public interface CommandeService {
    boolean creer(Commande commande);
    List<Commande> listerTous();
    Commande obtenirParId(int id);
    List<Commande> listerParClient(int clientId);
    boolean modifier(Commande commande);
    boolean archiver(int id);
}
