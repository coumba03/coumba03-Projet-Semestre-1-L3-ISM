package com.brasilburger.view;

import com.brasilburger.model.Zone;
import com.brasilburger.service.ZoneService;
import java.util.List;
import java.util.Scanner;

public class ZoneView {
    private final ZoneService service;
    private final Scanner scanner = new Scanner(System.in);

    public ZoneView(ZoneService service) {
        this.service = service;
    }

    public void start() {
        int choix;
        do {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║         GESTION DES ZONES              ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  1. Créer une zone                     ║");
            System.out.println("║  2. Lister toutes les zones            ║");
            System.out.println("║  3. Modifier une zone                  ║");
            System.out.println("║  4. Archiver une zone                  ║");
            System.out.println("║  0. Retour                             ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            if (choix == 1) creer();
            else if (choix == 2) lister();
            else if (choix == 3) modifier();
            else if (choix == 4) archiver();
        } while (choix != 0);
    }

    private void creer() {
        Zone z = new Zone();
        System.out.println("\n CRÉATION D'UNE ZONE");
        System.out.print("Nom de la zone : ");
        z.setNom(scanner.nextLine());
        System.out.print("Quartiers (séparés par virgule) : ");
        z.setQuartiers(scanner.nextLine());
        System.out.print("Prix de livraison : ");
        z.setPrixLivraison(scanner.nextDouble());
        scanner.nextLine();

        if (service.creer(z)) {
            System.out.println("✅ Zone créée !");
        } else {
            System.out.println("❌ Erreur lors de la création.");
        }
    }

    private void lister() {
        List<Zone> zones = service.listerTous();
        if (zones.isEmpty()) {
            System.out.println("Aucune zone trouvée.");
            return;
        }
        System.out.println("\n LISTE DES ZONES");
        System.out.println("═══════════════════════════════════════════════════════");
        for (Zone z : zones) {
            System.out.printf("ID: %d | %s | %s FCFA%n", z.getId(), z.getNom(), z.getPrixLivraison());
        }
    }

    private void modifier() {
        lister();
        System.out.print("\nID de la zone à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Zone z = service.obtenirParId(id);
        if (z == null) {
            System.out.println("❌ Zone non trouvée !");
            return;
        }
        System.out.print("Nouveau nom (appuyer Entrée pour garder) : ");
        String nom = scanner.nextLine();
        if (!nom.isBlank()) z.setNom(nom);

        if (service.modifier(z)) {
            System.out.println("✅ Zone modifiée !");
        }
    }

    private void archiver() {
        lister();
        System.out.print("\nID de la zone à archiver : ");
        int id = scanner.nextInt();
        if (service.archiver(id)) {
            System.out.println("✅ Zone archivée !");
        }
    }
}
