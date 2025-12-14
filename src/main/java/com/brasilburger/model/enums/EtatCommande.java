package com.brasilburger.model.enums;

/**
 * Énumération des états possibles d'une commande
 */
public enum EtatCommande {
    NEW("Nouvelle"),
    PAYÉ("Payée"),
    VALIDÉ("Validée"),
    PRÊT("Prête"),
    TERMINÉ("Terminée"),
    ANNULÉ("Annulée"),
    EN_LIVRAISON("En livraison");

    private final String label;

    EtatCommande(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static EtatCommande fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return EtatCommande.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
