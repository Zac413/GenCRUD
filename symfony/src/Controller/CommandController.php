<?php

namespace App\Controller;

use App\Entity\Produit;
use App\Entity\Command;
use App\Form\CommandType;
use App\Repository\CommandRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class CommandController extends AbstractController
{
    public function __construct(
        private EntityManagerInterface $em,
        private CommandRepository $repo
    ) {}

    #[Route('/command', name: 'command')]
    public function index(): Response
    {
        $list = $this->repo->findAll();
        return $this->render('command/index.html.twig', [
            'commands' => $list,
        ]);
    }

    #[Route('/command/create', name: 'command_create')]
    public function create(Request $request): Response
    {
        $entity = new Command();
        $form   = $this->createForm(CommandType::class, $entity);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
        
        foreach ($entity->getProduits() as $produit) {
            $produit->setCommand($entity);
        }
            $this->em->persist($entity);
            $this->em->flush();

            return $this->redirectToRoute('command');
        }

        return $this->render('command/create.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/command/edit/{id}', name: 'command_edit')]
    public function edit(Request $request, int $id): Response
    {
        $command = $this->repo->find($id);
        if (!$command) {
            throw $this->createNotFoundException('command not found');
        }

            
    $command->getProduits()->toArray();

        $form = $this->createForm(CommandType::class, $command);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->em->flush();

            return $this->redirectToRoute('command');
        }

        return $this->render('command/edit.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/command/delete/{id}', name: 'command_delete', methods: ['POST'])]
    public function delete(int $id): Response
    {
        $command = $this->repo->find($id);
        if ($command) {
            $this->em->remove($command);
            $this->em->flush();
        }

        return $this->redirectToRoute('command');
    }
}
