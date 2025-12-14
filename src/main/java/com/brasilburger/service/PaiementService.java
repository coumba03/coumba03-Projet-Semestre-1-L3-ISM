package com.brasilburger.service;

import com.brasilburger.model.Paiement;
import java.util.List;

public interface PaiementService {
    boolean creer(Paiement paiement);
    List<Paiement> listerTous();
    Paiement obtenirParId(int id);
    Paiement obtenirParCommande(int commandeId);
    List<Paiement> listerParClient(int clientId);
    boolean modifier(Paiement paiement);
}
