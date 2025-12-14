package com.brasilburger.service;

import com.brasilburger.model.CommandeItem;
import java.util.List;

public interface CommandeItemService {
    boolean creer(CommandeItem item);
    List<CommandeItem> listerTous();
    CommandeItem obtenirParId(int id);
    List<CommandeItem> listerParCommande(int commandeId);
    boolean modifier(CommandeItem item);
    boolean supprimer(int id);
}
