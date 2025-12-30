<?php

namespace App\Controller\Admin;

use App\Service\StatisticsServiceInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/admin')]
class DashboardController extends AbstractController
{
    #[Route('', name: 'admin_dashboard', methods: ['GET'])]
    public function index(StatisticsServiceInterface $statisticsService): Response
    {
        $stats = $statisticsService->getTodayStats();

        return $this->render('admin/dashboard.html.twig', [
            'stats' => $stats,
        ]);
    }
}
