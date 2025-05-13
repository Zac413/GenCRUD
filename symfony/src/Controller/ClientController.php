<?php

namespace App\Controller;

use App\Entity\Client;
use App\Form\ClientType;
use App\Repository\ClientRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ClientController extends AbstractController
{
    public function __construct(
        private EntityManagerInterface $em,
        private ClientRepository $repo
    ) {}

    #[Route('/client', name: 'client')]
    public function index(): Response
    {
        $list = $this->repo->findAll();
        return $this->render('client/index.html.twig', [
            'clients' => $list,
        ]);
    }

    #[Route('/client/create', name: 'client_create')]
    public function create(Request $request): Response
    {
        $entity = new Client();
        $form   = $this->createForm(ClientType::class, $entity);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $this->em->persist($entity);
            $this->em->flush();

            return $this->redirectToRoute('client');
        }

        return $this->render('client/create.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/client/edit/{id}', name: 'client_edit')]
    public function edit(Request $request, int $id): Response
    {
        $client = $this->repo->find($id);
        if (!$client) {
            throw $this->createNotFoundException('client not found');
        }

        $form = $this->createForm(ClientType::class, $client);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->em->flush();

            return $this->redirectToRoute('client');
        }

        return $this->render('client/edit.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/client/delete/{id}', name: 'client_delete', methods: ['POST'])]
    public function delete(int $id): Response
    {
        $client = $this->repo->find($id);
        if ($client) {
            $this->em->remove($client);
            $this->em->flush();
        }

        return $this->redirectToRoute('client');
    }
}
