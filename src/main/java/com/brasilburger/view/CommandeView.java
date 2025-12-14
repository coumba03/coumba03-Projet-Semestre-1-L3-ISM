package com.brasilburger.view;

import com.brasilburger.model.Commande;
import com.brasilburger.service.CommandeService;
import java.util.List;
import java.util.Scanner;

public class CommandeView {
    private final CommandeService service;
    private final Scanner scanner = new Scanner(System.in);

    public CommandeView(CommandeService service) {
        this.service = service;
    }

    public void start() {
        int choix;
        do {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║        GESTION DES COMMANDES           ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  1. Créer une commande                 ║");
            System.out.println("║  2. Lister toutes les commandes        ║");
            System.out.println("║  3. Modifier une commande              ║");
            System.out.println("║  4. Archiver une commande              ║");
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
        Commande cmd = new Commande();
        System.out.println("\n CRÉATION D'UNE COMMANDE");
        System.out.print("ID Client : ");
        cmd.setClientId(scanner.nextInt());
        System.out.print("Montant total : ");
        cmd.setMontantTotal(scanner.nextDouble());
        scanner.nextLine();
        System.out.print("Type (SUR_PLACE, A_EMPORTER, LIVRAISON) : ");
        String typeStr = scanner.nextLine();
        try {
            cmd.setTypeCommande(com.brasilburger.model.enums.TypeCommande.valueOf(typeStr));
        } catch (IllegalArgumentException e) {
            System.out.println("Type invalide.");
            return;
        }

        if (service.creer(cmd)) {
            System.out.println("✅ Commande créée !");
        } else {
            System.out.println("❌ Erreur lors de la création.");
        }
    }

    private void lister() {
        List<Commande> commandes = service.listerTous();
        if (commandes.isEmpty()) {
            System.out.println("Aucune commande trouvée.");
            return;
        }
        System.out.println("\n LISTE DES COMMANDES");
        System.out.println("═══════════════════════════════════════════════════════");
        for (Commande cmd : commandes) {
            System.out.printf("ID: %d | Client: %d | %.0f FCFA | %s%n", cmd.getId(), cmd.getClientId(), cmd.getMontantTotal(), cmd.getEtat());
        }
    }

    private void modifier() {
        lister();
        System.out.print("\nID de la commande à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Commande cmd = service.obtenirParId(id);
        if (cmd == null) {
            System.out.println("❌ Commande non trouvée !");
            return;
        }
        System.out.print("Nouvel état (appuyer Entrée pour garder) : ");
        String etatStr = scanner.nextLine();
        if (!etatStr.isBlank()) {
            try {
                cmd.setEtat(com.brasilburger.model.enums.EtatCommande.valueOf(etatStr));
            } catch (IllegalArgumentException e) {
                System.out.println("État invalide.");
                return;
            }
        }

        if (service.modifier(cmd)) {
            System.out.println("✅ Commande modifiée !");
        }
    }

    private void archiver() {
        lister();
        System.out.print("\nID de la commande à archiver : ");
        int id = scanner.nextInt();
        if (service.archiver(id)) {
            System.out.println("✅ Commande archivée !");
        }
    }
}
