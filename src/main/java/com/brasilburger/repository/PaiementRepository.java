package com.brasilburger.repository;

import com.brasilburger.model.Paiement;
import java.util.List;

public interface PaiementRepository {
    boolean creer(Paiement paiement);
    List<Paiement> listerTous();
    Paiement obtenirParId(int id);
    Paiement obtenirParCommande(int commandeId);
    List<Paiement> listerParClient(int clientId);
    boolean modifier(Paiement paiement);
}
