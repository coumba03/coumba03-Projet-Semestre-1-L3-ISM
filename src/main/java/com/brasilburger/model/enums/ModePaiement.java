package com.brasilburger.model.enums;

/**
 * Énumération des modes de paiement disponibles
 */
public enum ModePaiement {
    WAVE("Wave"),
    OM("Orange Money");

    private final String label;

    ModePaiement(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static ModePaiement fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return ModePaiement.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
