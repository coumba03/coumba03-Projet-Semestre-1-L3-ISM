<?php

namespace App\Service;

use App\Pagination\Paginator;

interface LivraisonServiceInterface
{
    public function getCommandesAffecter(Paginator $paginator): array;

    public function countCommandesAffecter(): int;

    public function affecter(int $commandeId, int $zoneId, int $livreurId): void;

    public function listLivraisons(array $filters, Paginator $paginator): array;

    public function countLivraisons(array $filters): int;

    public function updateStatut(int $livraisonId, string $statut): void;
}
