<?php

namespace App\Controller\Admin;

use App\Service\DataLookupServiceInterface;
use App\Service\LivraisonServiceInterface;
use App\Service\PaginationServiceInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/admin/livraisons')]
class LivraisonController extends AbstractController
{
    #[Route('', name: 'admin_livraisons_index', methods: ['GET'])]
    public function index(
        Request $request,
        LivraisonServiceInterface $livraisonService,
        DataLookupServiceInterface $dataLookupService,
        PaginationServiceInterface $paginationService
    ): Response {
        $page = max(1, $request->query->getInt('page', 1));
        $filters = [
            'statut' => $request->query->getString('statut', ''),
            'zone' => $request->query->getString('zone', ''),
            'livreur' => $request->query->getString('livreur', ''),
        ];

        $paginator = $paginationService->createPaginator($page, 15);
        $livraisons = $livraisonService->listLivraisons($filters, $paginator);
        $zones = $dataLookupService->getActiveZones();
        $livreurs = $dataLookupService->getActiveLivreurs();

        return $this->render('admin/livraisons/index.html.twig', [
            'livraisons' => $livraisons,
            'filters' => $filters,
            'zones' => $zones,
            'livreurs' => $livreurs,
            'paginator' => $paginator,
        ]);
    }

    #[Route('/affecter', name: 'admin_livraisons_affecter', methods: ['GET'])]
    public function affecterPage(
        Request $request,
        LivraisonServiceInterface $livraisonService,
        DataLookupServiceInterface $dataLookupService,
        PaginationServiceInterface $paginationService
    ): Response {
        $page = max(1, $request->query->getInt('page', 1));
        $paginator = $paginationService->createPaginator($page, 15);
        $commandes = $livraisonService->getCommandesAffecter($paginator);
        $zones = $dataLookupService->getActiveZones();
        $livreurs = $dataLookupService->getActiveLivreurs();

        return $this->render('admin/livraisons/affecter.html.twig', [
            'commandes' => $commandes,
            'zones' => $zones,
            'livreurs' => $livreurs,
            'paginator' => $paginator,
        ]);
    }

    #[Route('/affecter', name: 'admin_livraisons_affecter_post', methods: ['POST'])]
    public function affecter(Request $request, LivraisonServiceInterface $livraisonService): RedirectResponse
    {
        $commandeId = (int) $request->request->get('commande_id');
        $zoneId = (int) $request->request->get('zone_id');
        $livreurId = (int) $request->request->get('livreur_id');

        $livraisonService->affecter($commandeId, $zoneId, $livreurId);
        $this->addFlash('success', 'Livraison affectée');

        return $this->redirectToRoute('admin_livraisons_affecter');
    }

    #[Route('/{id}/statut', name: 'admin_livraisons_statut', requirements: ['id' => '\\d+'], methods: ['POST'])]
    public function updateStatut(int $id, Request $request, LivraisonServiceInterface $livraisonService): RedirectResponse
    {
        $statut = $request->request->getString('statut');
        $livraisonService->updateStatut($id, $statut);
        $this->addFlash('success', 'Statut mis à jour');

        return $this->redirectToRoute('admin_livraisons_index');
    }
}
