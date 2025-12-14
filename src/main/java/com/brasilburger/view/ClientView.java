package com.brasilburger.view;

import com.brasilburger.model.Client;
import com.brasilburger.service.ClientService;
import java.util.List;
import java.util.Scanner;

public class ClientView {
    private final ClientService service;
    private final Scanner scanner = new Scanner(System.in);

    public ClientView(ClientService service) {
        this.service = service;
    }

    public void start() {
        int choix;
        do {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║        GESTION DES CLIENTS             ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  1. Créer un client                    ║");
            System.out.println("║  2. Lister tous les clients            ║");
            System.out.println("║  3. Rechercher par ID                  ║");
            System.out.println("║  4. Modifier un client                 ║");
            System.out.println("║  5. Archiver un client                 ║");
            System.out.println("║  0. Retour                             ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            if (choix == 1) creer();
            else if (choix == 2) lister();
            else if (choix == 3) obtenirParId();
            else if (choix == 4) modifier();
            else if (choix == 5) archiver();
        } while (choix != 0);
    }

    private void creer() {
        Client c = new Client();
        System.out.println("\n CRÉATION D'UN CLIENT");
        System.out.print("Nom : ");
        c.setNom(scanner.nextLine());
        System.out.print("Prénom : ");
        c.setPrenom(scanner.nextLine());
        System.out.print("Email : ");
        c.setEmail(scanner.nextLine());
        System.out.print("Téléphone : ");
        c.setTelephone(scanner.nextLine());
        System.out.print("Adresse : ");
        c.setAdresse(scanner.nextLine());

        if (service.creer(c)) {
            System.out.println("✅ Client créé avec succès !");
        } else {
            System.out.println("❌ Erreur lors de la création.");
        }
    }

    private void lister() {
        List<Client> clients = service.listerTous();
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé.");
            return;
        }
        System.out.println("\n LISTE DES CLIENTS");
        System.out.println("═══════════════════════════════════════════════════════");
        for (Client c : clients) {
            System.out.printf("ID: %d | %s %s | %s | %s%n", c.getId(), c.getNom(), c.getPrenom(), c.getEmail(), c.getTelephone());
        }
    }

    private void obtenirParId() {
        System.out.print("ID du client : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Client c = service.obtenirParId(id);
        if (c == null) {
            System.out.println("❌ Client non trouvé !");
            return;
        }
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║        DÉTAILS DU CLIENT               ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ ID          : %-28d ║%n", c.getId());
        System.out.printf("║ Nom         : %-28s ║%n", c.getNom());
        System.out.printf("║ Prénom      : %-28s ║%n", c.getPrenom());
        System.out.printf("║ Email       : %-28s ║%n", c.getEmail());
        System.out.printf("║ Téléphone   : %-28s ║%n", c.getTelephone());
        System.out.printf("║ Adresse     : %-28s ║%n", c.getAdresse());
        System.out.println("╚════════════════════════════════════════╝");
    }

    private void modifier() {
        lister();
        System.out.print("\nID du client à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Client c = service.obtenirParId(id);
        if (c == null) {
            System.out.println("❌ Client non trouvé !");
            return;
        }
        System.out.print("Nouveau nom (appuyer Entrée pour garder) : ");
        String nom = scanner.nextLine();
        if (!nom.isBlank()) c.setNom(nom);

        System.out.print("Nouveau prénom : ");
        String prenom = scanner.nextLine();
        if (!prenom.isBlank()) c.setPrenom(prenom);

        if (service.modifier(c)) {
            System.out.println("✅ Client modifié !");
        }
    }

    private void archiver() {
        lister();
        System.out.print("\nID du client à archiver : ");
        int id = scanner.nextInt();
        if (service.archiver(id)) {
            System.out.println("✅ Client archivé !");
        }
    }
}
