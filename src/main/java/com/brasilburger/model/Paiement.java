package com.brasilburger.model;

import com.brasilburger.model.enums.ModePaiement;
import java.time.LocalDateTime;

/**
 * Classe représentant un Paiement
 */
public class Paiement {
    private int id;
    private int commandeId;
    private double montant;
    private ModePaiement modePaiement;
    private LocalDateTime datePaiement;
    private boolean valide;
    private String referenceTransaction;

    public Paiement() {
        this.datePaiement = LocalDateTime.now();
        this.valide = false;
    }

    public Paiement(int commandeId, double montant, ModePaiement modePaiement) {
        this.commandeId = commandeId;
        this.montant = montant;
        this.modePaiement = modePaiement;
        this.datePaiement = LocalDateTime.now();
        this.valide = false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCommandeId() { return commandeId; }
    public void setCommandeId(int commandeId) { this.commandeId = commandeId; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public ModePaiement getModePaiement() { return modePaiement; }
    public void setModePaiement(ModePaiement modePaiement) { this.modePaiement = modePaiement; }

    public LocalDateTime getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDateTime datePaiement) { this.datePaiement = datePaiement; }

    public boolean isValide() { return valide; }
    public void setValide(boolean valide) { this.valide = valide; }

    public String getReferenceTransaction() { return referenceTransaction; }
    public void setReferenceTransaction(String referenceTransaction) { this.referenceTransaction = referenceTransaction; }

    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", commandeId=" + commandeId +
                ", montant=" + montant +
                ", modePaiement=" + modePaiement +
                ", datePaiement=" + datePaiement +
                ", valide=" + valide +
                '}';
    }
}
