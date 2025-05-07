<?php

namespace App\Repository;

use App\Entity\{{ENTITY_NAME}};
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class {{ENTITY_NAME}}Repository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, {{ENTITY_NAME}}::class);
    }
}