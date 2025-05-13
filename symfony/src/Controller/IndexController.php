<?php

namespace App\Controller;

use App\Repository\ProduitRepository;
use App\Repository\CommandRepository;
use App\Repository\ClientRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class IndexController extends AbstractController
{
    public function __construct(
        private ProduitRepository $produitRepo,
        private CommandRepository $commandRepo,
        private ClientRepository $clientRepo
    ) {}

    #[Route('/', name: 'app_index')]
    public function index(): Response
    {
        return $this->render('index/index.html.twig', [
            'produits' => $this->produitRepo->findAll(),
            'commands' => $this->commandRepo->findAll(),
            'clients'  => $this->clientRepo->findAll(),
        ]);
    }
}
