<?php

namespace App\Controller\Admin;

use App\Service\CommandeServiceInterface;
use App\Service\DataLookupServiceInterface;
use App\Service\PaginationServiceInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/admin/commandes')]
class CommandeController extends AbstractController
{
    #[Route('', name: 'admin_commandes_index', methods: ['GET'])]
    public function index(
        Request $request,
        CommandeServiceInterface $commandeService,
        DataLookupServiceInterface $dataLookupService,
        PaginationServiceInterface $paginationService
    ): Response {
        $page = max(1, $request->query->getInt('page', 1));
        $filters = [
            'etat' => $request->query->getString('etat', ''),
            'date' => $request->query->getString('date', ''),
            'client' => $request->query->getString('client', ''),
            'type_item' => $request->query->getString('type_item', ''),
            'burger_id' => $request->query->getString('burger_id', ''),
            'menu_id' => $request->query->getString('menu_id', ''),
        ];

        $paginator = $paginationService->createPaginator($page, 15);
        $commandes = $commandeService->search($filters, $paginator);
        $burgers = $dataLookupService->getActiveBurgers();
        $menus = $dataLookupService->getActiveMenus();

        return $this->render('admin/commandes/index.html.twig', [
            'commandes' => $commandes,
            'filters' => $filters,
            'burgers' => $burgers,
            'menus' => $menus,
            'paginator' => $paginator,
        ]);
    }

    #[Route('/{id}', name: 'admin_commandes_show', requirements: ['id' => '\\d+'], methods: ['GET'])]
    public function show(int $id, CommandeServiceInterface $commandeService): Response
    {
        $commande = $commandeService->findById($id);
        if (!$commande) {
            throw $this->createNotFoundException('Commande non trouvée');
        }

        return $this->render('admin/commandes/show.html.twig', [
            'commande' => $commande,
        ]);
    }

    #[Route('/{id}/etat', name: 'admin_commandes_etat', requirements: ['id' => '\\d+'], methods: ['POST'])]
    public function updateEtat(int $id, Request $request, CommandeServiceInterface $commandeService): RedirectResponse
    {
        $etat = $request->request->getString('etat');
        $commandeService->updateEtat($id, $etat);

        return $this->redirectToRoute('admin_commandes_show', ['id' => $id]);
    }

    #[Route('/{id}/annuler', name: 'admin_commandes_annuler', requirements: ['id' => '\\d+'], methods: ['POST'])]
    public function annuler(int $id, CommandeServiceInterface $commandeService): RedirectResponse
    {
        $commandeService->annuler($id);

        return $this->redirectToRoute('admin_commandes_show', ['id' => $id]);
    }
}
