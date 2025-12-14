package com.brasilburger.model;

import com.brasilburger.model.enums.TypeItem;

/**
 * Classe représentant un article dans une commande
 */
public class CommandeItem {
    private int id;
    private int commandeId;
    private TypeItem typeItem;
    private int itemId;
    private int quantite;
    private double prixUnitaire;
    private double sousTotal;

    public CommandeItem() {
    }

    public CommandeItem(int commandeId, TypeItem typeItem, int itemId, int quantite, double prixUnitaire) {
        this.commandeId = commandeId;
        this.typeItem = typeItem;
        this.itemId = itemId;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.sousTotal = quantite * prixUnitaire;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCommandeId() { return commandeId; }
    public void setCommandeId(int commandeId) { this.commandeId = commandeId; }

    public TypeItem getTypeItem() { return typeItem; }
    public void setTypeItem(TypeItem typeItem) { this.typeItem = typeItem; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public double getSousTotal() { return sousTotal; }
    public void setSousTotal(double sousTotal) { this.sousTotal = sousTotal; }

    @Override
    public String toString() {
        return "CommandeItem{" +
                "id=" + id +
                ", typeItem=" + typeItem +
                ", quantite=" + quantite +
                ", sousTotal=" + sousTotal +
                '}';
    }
}
