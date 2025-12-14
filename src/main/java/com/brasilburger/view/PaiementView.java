package com.brasilburger.view;

import com.brasilburger.model.Paiement;
import com.brasilburger.service.PaiementService;
import java.util.List;
import java.util.Scanner;

public class PaiementView {
    private final PaiementService service;
    private final Scanner scanner = new Scanner(System.in);

    public PaiementView(PaiementService service) {
        this.service = service;
    }

    public void start() {
        int choix;
        do {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║       GESTION DES PAIEMENTS            ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  1. Créer un paiement                  ║");
            System.out.println("║  2. Lister tous les paiements          ║");
            System.out.println("║  3. Modifier un paiement               ║");
            System.out.println("║  0. Retour                             ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            if (choix == 1) creer();
            else if (choix == 2) lister();
            else if (choix == 3) modifier();
        } while (choix != 0);
    }

    private void creer() {
        Paiement p = new Paiement();
        System.out.println("\n CRÉATION D'UN PAIEMENT");
        System.out.print("ID Commande : ");
        p.setCommandeId(scanner.nextInt());
        System.out.print("Montant : ");
        p.setMontant(scanner.nextDouble());
        scanner.nextLine();
        System.out.print("Mode (WAVE, OM) : ");
        String modeStr = scanner.nextLine();
        try {
            p.setModePaiement(com.brasilburger.model.enums.ModePaiement.valueOf(modeStr));
        } catch (IllegalArgumentException e) {
            System.out.println("Mode invalide.");
            return;
        }

        if (service.creer(p)) {
            System.out.println("✅ Paiement créé !");
        } else {
            System.out.println("❌ Erreur lors de la création.");
        }
    }

    private void lister() {
        List<Paiement> paiements = service.listerTous();
        if (paiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé.");
            return;
        }
        System.out.println("\n LISTE DES PAIEMENTS");
        System.out.println("═══════════════════════════════════════════════════════");
        for (Paiement p : paiements) {
            System.out.printf("ID: %d | Commande: %d | %.0f FCFA | %s%n", p.getId(), p.getCommandeId(), p.getMontant(), p.getModePaiement());
        }
    }

    private void modifier() {
        lister();
        System.out.print("\nID du paiement à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Paiement p = service.obtenirParId(id);
        if (p == null) {
            System.out.println("❌ Paiement non trouvé !");
            return;
        }
        System.out.print("Nouveau montant (appuyer Entrée pour garder) : ");
        String montantStr = scanner.nextLine();
        if (!montantStr.isBlank()) {
            p.setMontant(Double.parseDouble(montantStr));
        }

        if (service.modifier(p)) {
            System.out.println("✅ Paiement modifié !");
        }
    }
}
