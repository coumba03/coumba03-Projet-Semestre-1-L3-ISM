<?php

namespace App\Service;

use App\Entity\Burger;
use App\Entity\Livreur;
use App\Entity\Menu;
use App\Entity\Zone;

/**
 * Service pour récupérer les données de référence
 * Centralise l'accès aux données de configuration
 */
interface DataLookupServiceInterface
{
    /**
     * Get all active burgers sorted by name
     */
    public function getActiveBurgers(): array;

    /**
     * Get all active menus sorted by name
     */
    public function getActiveMenus(): array;

    /**
     * Get all active zones sorted by name
     */
    public function getActiveZones(): array;

    /**
     * Get all active livreurs sorted by name
     */
    public function getActiveLivreurs(): array;
}
