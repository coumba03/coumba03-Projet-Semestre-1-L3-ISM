package com.brasilburger.model.enums;

/**
 * Énumération des types d'articles (burger ou menu)
 */
public enum TypeItem {
    BURGER("Burger"),
    MENU("Menu");

    private final String label;

    TypeItem(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static TypeItem fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return TypeItem.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
