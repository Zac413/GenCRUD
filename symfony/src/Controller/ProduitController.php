<?php

namespace App\Controller;

use App\Entity\Produit;
use App\Form\ProduitType;
use App\Repository\ProduitRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ProduitController extends AbstractController
{
    public function __construct(
        private EntityManagerInterface $em,
        private ProduitRepository $repo
    ) {}

    #[Route('/produit', name: 'produit')]
    public function index(): Response
    {
        $list = $this->repo->findAll();
        return $this->render('produit/index.html.twig', [
            'produits' => $list,
        ]);
    }

    #[Route('/produit/create', name: 'produit_create')]
    public function create(Request $request): Response
    {
        $entity = new Produit();
        $form   = $this->createForm(ProduitType::class, $entity);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $this->em->persist($entity);
            $this->em->flush();

            return $this->redirectToRoute('produit');
        }

        return $this->render('produit/create.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/produit/edit/{id}', name: 'produit_edit')]
    public function edit(Request $request, int $id): Response
    {
        $produit = $this->repo->find($id);
        if (!$produit) {
            throw $this->createNotFoundException('produit not found');
        }

        $form = $this->createForm(ProduitType::class, $produit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->em->flush();

            return $this->redirectToRoute('produit');
        }

        return $this->render('produit/edit.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/produit/delete/{id}', name: 'produit_delete', methods: ['POST'])]
    public function delete(int $id): Response
    {
        $produit = $this->repo->find($id);
        if ($produit) {
            $this->em->remove($produit);
            $this->em->flush();
        }

        return $this->redirectToRoute('produit');
    }
}
