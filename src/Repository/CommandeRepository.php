<?php

namespace App\Repository;

use App\Entity\Commande;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class CommandeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Commande::class);
    }

    /**
     * Search commandes with filters
     */
    public function search(array $filters, int $offset = 0, int $limit = 15): array
    {
        $qb = $this->createQueryBuilder('c')
            ->select('DISTINCT c, cl, p')
            ->leftJoin('c.client', 'cl')
            ->leftJoin('c.paiement', 'p')
            ->andWhere('c.archived = false')
            ->orderBy('c.dateCommande', 'DESC');

        $needsItemsJoin = ($filters['type_item'] ?? '') !== '' || ($filters['burger_id'] ?? '') !== '' || ($filters['menu_id'] ?? '') !== '';
        if ($needsItemsJoin) {
            $qb->leftJoin('c.items', 'it');
        }

        if ($filters['etat'] !== '') {
            $qb->andWhere('c.etat = :etat')->setParameter('etat', $filters['etat']);
        }

        if ($filters['date'] !== '') {
            $start = \DateTimeImmutable::createFromFormat('Y-m-d', $filters['date']);
            if ($start instanceof \DateTimeImmutable) {
                $end = $start->setTime(23, 59, 59);
                $start = $start->setTime(0, 0, 0);
                $qb->andWhere('c.dateCommande BETWEEN :start AND :end')
                    ->setParameter('start', $start)
                    ->setParameter('end', $end);
            }
        }

        if ($filters['client'] !== '') {
            $q = '%' . mb_strtolower($filters['client']) . '%';
            $qb->andWhere('LOWER(cl.nom) LIKE :q OR LOWER(cl.prenom) LIKE :q OR LOWER(cl.telephone) LIKE :q')
                ->setParameter('q', $q);
        }

        if (($filters['type_item'] ?? '') !== '') {
            $qb->andWhere('it.typeItem = :typeItem')
                ->setParameter('typeItem', $filters['type_item']);
        }

        if (($filters['burger_id'] ?? '') !== '') {
            $qb->andWhere('it.typeItem = :burgerType AND it.itemId = :burgerId')
                ->setParameter('burgerType', 'BURGER')
                ->setParameter('burgerId', (int) $filters['burger_id']);
        }

        if (($filters['menu_id'] ?? '') !== '') {
            $qb->andWhere('it.typeItem = :menuType AND it.itemId = :menuId')
                ->setParameter('menuType', 'MENU')
                ->setParameter('menuId', (int) $filters['menu_id']);
        }

        return $qb->setFirstResult($offset)
            ->setMaxResults($limit)
            ->getQuery()
            ->getResult();
    }

    /**
     * Count commandes with filters
     */
    public function countByFilters(array $filters): int
    {
        $qb = $this->createQueryBuilder('c')
            ->select('COUNT(DISTINCT c)')
            ->leftJoin('c.client', 'cl')
            ->andWhere('c.archived = false');

        $needsItemsJoin = ($filters['type_item'] ?? '') !== '' || ($filters['burger_id'] ?? '') !== '' || ($filters['menu_id'] ?? '') !== '';
        if ($needsItemsJoin) {
            $qb->leftJoin('c.items', 'it');
        }

        if ($filters['etat'] !== '') {
            $qb->andWhere('c.etat = :etat')->setParameter('etat', $filters['etat']);
        }

        if ($filters['date'] !== '') {
            $start = \DateTimeImmutable::createFromFormat('Y-m-d', $filters['date']);
            if ($start instanceof \DateTimeImmutable) {
                $end = $start->setTime(23, 59, 59);
                $start = $start->setTime(0, 0, 0);
                $qb->andWhere('c.dateCommande BETWEEN :start AND :end')
                    ->setParameter('start', $start)
                    ->setParameter('end', $end);
            }
        }

        if ($filters['client'] !== '') {
            $q = '%' . mb_strtolower($filters['client']) . '%';
            $qb->andWhere('LOWER(cl.nom) LIKE :q OR LOWER(cl.prenom) LIKE :q OR LOWER(cl.telephone) LIKE :q')
                ->setParameter('q', $q);
        }

        if (($filters['type_item'] ?? '') !== '') {
            $qb->andWhere('it.typeItem = :typeItem')
                ->setParameter('typeItem', $filters['type_item']);
        }

        if (($filters['burger_id'] ?? '') !== '') {
            $qb->andWhere('it.typeItem = :burgerType AND it.itemId = :burgerId')
                ->setParameter('burgerType', 'BURGER')
                ->setParameter('burgerId', (int) $filters['burger_id']);
        }

        if (($filters['menu_id'] ?? '') !== '') {
            $qb->andWhere('it.typeItem = :menuType AND it.itemId = :menuId')
                ->setParameter('menuType', 'MENU')
                ->setParameter('menuId', (int) $filters['menu_id']);
        }

        return (int) $qb->getQuery()->getSingleScalarResult();
    }

    /**
     * Find commande by ID
     */
    public function findById(int $id): ?Commande
    {
        return $this->find($id);
    }
}
