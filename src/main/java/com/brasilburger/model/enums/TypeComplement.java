package com.brasilburger.model.enums;

/**
 * Énumération des types de compléments (boissons, frites)
 */
public enum TypeComplement {
    BOISSON("Boisson"),
    FRITE("Frite");

    private final String label;

    TypeComplement(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Convertir une chaîne en enum TypeComplement
     */
    public static TypeComplement fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return TypeComplement.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
