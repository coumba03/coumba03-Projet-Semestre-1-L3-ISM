package com.brasilburger.repository;

import com.brasilburger.model.CommandeItem;
import java.util.List;

public interface CommandeItemRepository {
    boolean creer(CommandeItem item);
    List<CommandeItem> listerTous();
    CommandeItem obtenirParId(int id);
    List<CommandeItem> listerParCommande(int commandeId);
    boolean modifier(CommandeItem item);
    boolean supprimer(int id);
}
