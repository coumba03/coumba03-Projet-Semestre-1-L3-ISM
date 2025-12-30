<?php

namespace App\Service\impl;

use App\Pagination\Paginator;
use App\Service\PaginationServiceInterface;

class PaginationService implements PaginationServiceInterface
{
    public function createPaginator(int $page = 1, int $perPage = 15): Paginator
    {
        return new Paginator($page, $perPage);
    }
}
