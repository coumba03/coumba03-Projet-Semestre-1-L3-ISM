package com.brasilburger.model;

import com.brasilburger.model.enums.EtatCommande;
import com.brasilburger.model.enums.TypeCommande;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une Commande
 */
public class Commande {
    private int id;
    private int clientId;
    private TypeCommande typeCommande;
    private EtatCommande etat;
    private LocalDateTime dateCommande;
    private double montantTotal;
    private boolean archived;
    private List<CommandeItem> items;

    public Commande() {
        this.items = new ArrayList<>();
        this.dateCommande = LocalDateTime.now();
        this.etat = EtatCommande.NEW;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public TypeCommande getTypeCommande() { return typeCommande; }
    public void setTypeCommande(TypeCommande typeCommande) { this.typeCommande = typeCommande; }

    public EtatCommande getEtat() { return etat; }
    public void setEtat(EtatCommande etat) { this.etat = etat; }

    public LocalDateTime getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDateTime dateCommande) { this.dateCommande = dateCommande; }

    public double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(double montantTotal) { this.montantTotal = montantTotal; }

    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }

    public List<CommandeItem> getItems() { return items; }
    public void setItems(List<CommandeItem> items) { this.items = items; }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", typeCommande=" + typeCommande +
                ", etat=" + etat +
                ", montantTotal=" + montantTotal +
                '}';
    }
}
