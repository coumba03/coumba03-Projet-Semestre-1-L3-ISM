package com.brasilburger.service;

import com.brasilburger.model.Complement;
import com.brasilburger.model.enums.TypeComplement;
import java.util.List;

public interface ComplementService {
    boolean creer(Complement complement);
    List<Complement> listerTous();
    List<Complement> listerParType(String type);
    Complement obtenirParId(int id);
    boolean modifier(Complement complement);
    boolean archiver(int id);
    
    /**
     * Lister compléments par type enum (convenience method)
     */
    default List<Complement> listerParTypeEnum(TypeComplement typeEnum) {
        return typeEnum != null ? listerParType(typeEnum.name()) : List.of();
    }
}
