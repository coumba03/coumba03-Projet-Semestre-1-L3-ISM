package com.brasilburger;

import com.brasilburger.repository.BurgerRepository;
import com.brasilburger.repository.ComplementRepository;
import com.brasilburger.repository.MenuRepository;
import com.brasilburger.repository.ClientRepository;
import com.brasilburger.repository.GestionnaireRepository;
import com.brasilburger.repository.LivreurRepository;
import com.brasilburger.repository.ZoneRepository;
import com.brasilburger.repository.CommandeRepository;
import com.brasilburger.repository.PaiementRepository;
import com.brasilburger.service.BurgerService;
import com.brasilburger.service.ComplementService;
import com.brasilburger.service.MenuService;
import com.brasilburger.service.ImageService;
import com.brasilburger.service.ClientService;
import com.brasilburger.service.GestionnaireService;
import com.brasilburger.service.LivreurService;
import com.brasilburger.service.ZoneService;
import com.brasilburger.service.CommandeService;
import com.brasilburger.service.PaiementService;
import com.brasilburger.view.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        BurgerRepository burgerRepo = new com.brasilburger.repository.impl.BurgerRepositoryImpl();
        ComplementRepository complementRepo = new com.brasilburger.repository.impl.ComplementRepositoryImpl();
        MenuRepository menuRepo = new com.brasilburger.repository.impl.MenuRepositoryImpl();
        ClientRepository clientRepo = new com.brasilburger.repository.impl.ClientRepositoryImpl();
        GestionnaireRepository gestionnaireRepo = new com.brasilburger.repository.impl.GestionnaireRepositoryImpl();
        LivreurRepository livreurRepo = new com.brasilburger.repository.impl.LivreurRepositoryImpl();
        ZoneRepository zoneRepo = new com.brasilburger.repository.impl.ZoneRepositoryImpl();
        CommandeRepository commandeRepo = new com.brasilburger.repository.impl.CommandeRepositoryImpl();
        PaiementRepository paiementRepo = new com.brasilburger.repository.impl.PaiementRepositoryImpl();
        ImageService imageService = new com.brasilburger.service.impl.ImageServiceImpl();

        BurgerService burgerService = new com.brasilburger.service.impl.BurgerServiceImpl(burgerRepo);
        ComplementService complementService = new com.brasilburger.service.impl.ComplementServiceImpl(complementRepo);
        MenuService menuService = new com.brasilburger.service.impl.MenuServiceImpl(menuRepo, burgerRepo, complementRepo);
        ClientService clientService = new com.brasilburger.service.impl.ClientServiceImpl(clientRepo);
        GestionnaireService gestionnaireService = new com.brasilburger.service.impl.GestionnaireServiceImpl(gestionnaireRepo);
        LivreurService livreurService = new com.brasilburger.service.impl.LivreurServiceImpl(livreurRepo);
        ZoneService zoneService = new com.brasilburger.service.impl.ZoneServiceImpl(zoneRepo);
        CommandeService commandeService = new com.brasilburger.service.impl.CommandeServiceImpl(commandeRepo);
        PaiementService paiementService = new com.brasilburger.service.impl.PaiementServiceImpl(paiementRepo);

        ConsoleUI ui = new ConsoleUI(burgerService, complementService, menuService, imageService,
                clientService, gestionnaireService, livreurService, zoneService, commandeService, paiementService);
        ui.start();
    }
}