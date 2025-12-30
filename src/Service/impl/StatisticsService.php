<?php

namespace App\Service\impl;

use App\Repository\StatisticsRepository;
use App\Service\StatisticsServiceInterface;

class StatisticsService implements StatisticsServiceInterface
{
    public function __construct(private readonly StatisticsRepository $statisticsRepository)
    {
    }

    public function getTodayStats(): array
    {
        return $this->statisticsRepository->getTodayStats();
    }
}
