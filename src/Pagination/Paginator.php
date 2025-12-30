<?php

namespace App\Pagination;

/**
 * Classe pour gérer la pagination
 */
class Paginator
{
    private int $page;
    private int $perPage;
    private int $total;

    public function __construct(int $page = 1, int $perPage = 15)
    {
        $this->page = max(1, $page);
        $this->perPage = max(1, $perPage);
        $this->total = 0;
    }

    /**
     * Get current page
     */
    public function getPage(): int
    {
        return $this->page;
    }

    /**
     * Get items per page
     */
    public function getPerPage(): int
    {
        return $this->perPage;
    }

    /**
     * Get offset for query
     */
    public function getOffset(): int
    {
        return ($this->page - 1) * $this->perPage;
    }

    /**
     * Get limit for query
     */
    public function getLimit(): int
    {
        return $this->perPage;
    }

    /**
     * Set total items count
     */
    public function setTotal(int $total): self
    {
        $this->total = max(0, $total);
        return $this;
    }

    /**
     * Get total items
     */
    public function getTotal(): int
    {
        return $this->total;
    }

    /**
     * Get total pages
     */
    public function getPageCount(): int
    {
        if ($this->total === 0) {
            return 1;
        }
        return (int) ceil($this->total / $this->perPage);
    }

    /**
     * Check if has previous page
     */
    public function hasPreviousPage(): bool
    {
        return $this->page > 1;
    }

    /**
     * Get previous page number
     */
    public function getPreviousPage(): int
    {
        return max(1, $this->page - 1);
    }

    /**
     * Check if has next page
     */
    public function hasNextPage(): bool
    {
        return $this->page < $this->getPageCount();
    }

    /**
     * Get next page number
     */
    public function getNextPage(): int
    {
        return min($this->getPageCount(), $this->page + 1);
    }

    /**
     * Check if page is valid
     */
    public function isValid(): bool
    {
        return $this->page >= 1 && $this->page <= $this->getPageCount();
    }

    /**
     * Get pages range for display (e.g. [1,2,3,4,5])
     */
    public function getPagesRange(int $delta = 2): array
    {
        $low = max(1, $this->page - $delta);
        $high = min($this->getPageCount(), $this->page + $delta);

        $pages = [];
        for ($i = $low; $i <= $high; $i++) {
            $pages[] = $i;
        }

        return $pages;
    }

    /**
     * Get start item number
     */
    public function getStartItem(): int
    {
        if ($this->total === 0) {
            return 0;
        }
        return $this->getOffset() + 1;
    }

    /**
     * Get end item number
     */
    public function getEndItem(): int
    {
        return min($this->getOffset() + $this->perPage, $this->total);
    }
}
