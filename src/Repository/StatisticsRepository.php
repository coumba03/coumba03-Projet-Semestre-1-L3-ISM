<?php

namespace App\Repository;

use App\Entity\Commande;
use Doctrine\DBAL\Connection;
use Doctrine\DBAL\ArrayParameterType;
use Doctrine\DBAL\Types\Types;

class StatisticsRepository
{
    public function __construct(private readonly Connection $connection)
    {
    }

    /**
     * Get today statistics
     */
    public function getTodayStats(): array
    {
        $start = (new \DateTimeImmutable('today'))->setTime(0, 0, 0);
        $end = $start->setTime(23, 59, 59);

        return [
            'commandes_en_cours' => (int) $this->getCommandesEnCours($start, $end),
            'commandes_validees' => (int) $this->getCommandesValidees($start, $end),
            'commandes_annulees' => (int) $this->getCommandesAnnulees($start, $end),
            'recettes' => (float) $this->getRecettes($start, $end),
            'top_burgers' => $this->getTopBurgers($start, $end),
            'top_menus' => $this->getTopBurgersMenu($start, $end),
        ];
    }

    /**
     * Get commandes en cours count
     */
    private function getCommandesEnCours(\DateTimeImmutable $start, \DateTimeImmutable $end): int
    {
        return (int) $this->connection->fetchOne(
            "
            SELECT COUNT(*)
            FROM commandes c
            WHERE c.archived = false
              AND c.date_commande BETWEEN :start AND :end
              AND c.etat IN (:etats)
            ",
            [
                'start' => $start,
                'end' => $end,
                'etats' => [
                    Commande::ETAT_NEW,
                    Commande::ETAT_VALIDEE,
                    Commande::ETAT_EN_PREPARATION,
                ],
            ],
            [
                'start' => Types::DATETIME_IMMUTABLE,
                'end' => Types::DATETIME_IMMUTABLE,
                'etats' => ArrayParameterType::STRING,
            ]
        );
    }

    /**
     * Get commandes validées count
     */
    private function getCommandesValidees(\DateTimeImmutable $start, \DateTimeImmutable $end): int
    {
        return (int) $this->connection->fetchOne(
            "
            SELECT COUNT(*)
            FROM commandes c
            WHERE c.archived = false
              AND c.date_commande BETWEEN :start AND :end
              AND c.etat = :etat
            ",
            [
                'start' => $start,
                'end' => $end,
                'etat' => Commande::ETAT_VALIDEE,
            ],
            [
                'start' => Types::DATETIME_IMMUTABLE,
                'end' => Types::DATETIME_IMMUTABLE,
            ]
        );
    }

    /**
     * Get commandes annulées count
     */
    private function getCommandesAnnulees(\DateTimeImmutable $start, \DateTimeImmutable $end): int
    {
        return (int) $this->connection->fetchOne(
            "
            SELECT COUNT(*)
            FROM commandes c
            WHERE c.archived = false
              AND c.date_commande BETWEEN :start AND :end
              AND c.etat = :etat
            ",
            [
                'start' => $start,
                'end' => $end,
                'etat' => Commande::ETAT_ANNULEE,
            ],
            [
                'start' => Types::DATETIME_IMMUTABLE,
                'end' => Types::DATETIME_IMMUTABLE,
            ]
        );
    }

    /**
     * Get today's revenue
     */
    private function getRecettes(\DateTimeImmutable $start, \DateTimeImmutable $end): float
    {
        return (float) $this->connection->fetchOne(
            "
            SELECT COALESCE(SUM(p.montant), 0)
            FROM paiements p
            WHERE p.valide = true
              AND p.date_paiement BETWEEN :start AND :end
            ",
            [
                'start' => $start,
                'end' => $end,
            ],
            [
                'start' => Types::DATETIME_IMMUTABLE,
                'end' => Types::DATETIME_IMMUTABLE,
            ]
        );
    }

    /**
     * Get top 5 burgers sold today
     */
    private function getTopBurgers(\DateTimeImmutable $start, \DateTimeImmutable $end): array
    {
        return $this->connection->fetchAllAssociative(
            "
            SELECT b.id, b.nom, SUM(ci.quantite) AS total_quantite
            FROM commande_items ci
            INNER JOIN commandes c ON c.id = ci.commande_id
            INNER JOIN burgers b ON b.id = ci.item_id
            WHERE c.archived = false
              AND c.date_commande BETWEEN :start AND :end
              AND ci.type_item = 'BURGER'
            GROUP BY b.id, b.nom
            ORDER BY total_quantite DESC
            LIMIT 5
            ",
            [
                'start' => $start,
                'end' => $end,
            ],
            [
                'start' => Types::DATETIME_IMMUTABLE,
                'end' => Types::DATETIME_IMMUTABLE,
            ]
        );
    }

    /**
     * Get top 5 menus sold today
     */
    private function getTopBurgersMenu(\DateTimeImmutable $start, \DateTimeImmutable $end): array
    {
        return $this->connection->fetchAllAssociative(
            "
            SELECT m.id, m.nom, SUM(ci.quantite) AS total_quantite
            FROM commande_items ci
            INNER JOIN commandes c ON c.id = ci.commande_id
            INNER JOIN menus m ON m.id = ci.item_id
            WHERE c.archived = false
              AND c.date_commande BETWEEN :start AND :end
              AND ci.type_item = 'MENU'
            GROUP BY m.id, m.nom
            ORDER BY total_quantite DESC
            LIMIT 5
            ",
            [
                'start' => $start,
                'end' => $end,
            ],
            [
                'start' => Types::DATETIME_IMMUTABLE,
                'end' => Types::DATETIME_IMMUTABLE,
            ]
        );
    }
}
