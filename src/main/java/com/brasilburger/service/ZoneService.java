package com.brasilburger.service;

import com.brasilburger.model.Zone;
import java.util.List;

public interface ZoneService {
    boolean creer(Zone zone);
    List<Zone> listerTous();
    Zone obtenirParId(int id);
    boolean modifier(Zone zone);
    boolean archiver(int id);
}
