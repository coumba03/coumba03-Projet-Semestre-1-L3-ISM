package com.brasilburger.model.enums;

/**
 * Énumération des types de commande (mode de retrait/livraison)
 */
public enum TypeCommande {
    SUR_PLACE("Sur place"),
    A_EMPORTER("À emporter"),
    LIVRAISON("Livraison");

    private final String label;

    TypeCommande(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static TypeCommande fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return TypeCommande.valueOf(value.toUpperCase().replace(" ", "_").replace("-", "_"));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
