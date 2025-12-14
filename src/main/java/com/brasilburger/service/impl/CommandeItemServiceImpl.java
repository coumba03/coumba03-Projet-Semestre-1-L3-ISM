package com.brasilburger.service.impl;

import com.brasilburger.model.CommandeItem;
import com.brasilburger.repository.CommandeItemRepository;
import com.brasilburger.service.CommandeItemService;
import java.util.List;

public class CommandeItemServiceImpl implements CommandeItemService {
    private final CommandeItemRepository repository;

    public CommandeItemServiceImpl(CommandeItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean creer(CommandeItem item) {
        return repository.creer(item);
    }

    @Override
    public List<CommandeItem> listerTous() {
        return repository.listerTous();
    }

    @Override
    public CommandeItem obtenirParId(int id) {
        return repository.obtenirParId(id);
    }

    @Override
    public List<CommandeItem> listerParCommande(int commandeId) {
        return repository.listerParCommande(commandeId);
    }

    @Override
    public boolean modifier(CommandeItem item) {
        return repository.modifier(item);
    }

    @Override
    public boolean supprimer(int id) {
        return repository.supprimer(id);
    }
}
