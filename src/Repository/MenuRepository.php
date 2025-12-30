<?php

namespace App\Repository;

use App\Entity\Menu;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class MenuRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Menu::class);
    }

    /**
     * Get active menus sorted by name
     */
    public function findActive(): array
    {
        return $this->findBy(['archived' => false], ['nom' => 'ASC']);
    }

    /**
     * Find menu by ID
     */
    public function findById(int $id): ?Menu
    {
        return $this->find($id);
    }
}
