package com.brasilburger.repository;

import com.brasilburger.model.Zone;
import java.util.List;

public interface ZoneRepository {
    boolean creer(Zone zone);
    List<Zone> listerTous();
    Zone obtenirParId(int id);
    boolean modifier(Zone zone);
    boolean archiver(int id);
}
