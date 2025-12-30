<?php

namespace App\Service\impl;

use App\Repository\BurgerRepository;
use App\Repository\LivreurRepository;
use App\Repository\MenuRepository;
use App\Repository\ZoneRepository;
use App\Service\DataLookupServiceInterface;

class DataLookupService implements DataLookupServiceInterface
{
    public function __construct(
        private readonly BurgerRepository $burgerRepository,
        private readonly MenuRepository $menuRepository,
        private readonly ZoneRepository $zoneRepository,
        private readonly LivreurRepository $livreurRepository
    ) {
    }

    public function getActiveBurgers(): array
    {
        return $this->burgerRepository->findActive();
    }

    public function getActiveMenus(): array
    {
        return $this->menuRepository->findActive();
    }

    public function getActiveZones(): array
    {
        return $this->zoneRepository->findActive();
    }

    public function getActiveLivreurs(): array
    {
        return $this->livreurRepository->findActive();
    }
}
