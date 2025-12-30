<?php

namespace App\Service;

use App\Pagination\Paginator;

interface PaginationServiceInterface
{
    /**
     * Create paginator from request page
     */
    public function createPaginator(int $page = 1, int $perPage = 15): Paginator;
}
