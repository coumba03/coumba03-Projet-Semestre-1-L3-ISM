package com.brasilburger.service.impl;

import com.brasilburger.model.Zone;
import com.brasilburger.repository.ZoneRepository;
import com.brasilburger.service.ZoneService;
import java.util.List;

public class ZoneServiceImpl implements ZoneService {
    private final ZoneRepository repository;

    public ZoneServiceImpl(ZoneRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean creer(Zone zone) {
        return repository.creer(zone);
    }

    @Override
    public List<Zone> listerTous() {
        return repository.listerTous();
    }

    @Override
    public Zone obtenirParId(int id) {
        return repository.obtenirParId(id);
    }

    @Override
    public boolean modifier(Zone zone) {
        return repository.modifier(zone);
    }

    @Override
    public boolean archiver(int id) {
        return repository.archiver(id);
    }
}
