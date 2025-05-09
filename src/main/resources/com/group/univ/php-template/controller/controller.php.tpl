<?php

namespace App\Controller;

use App\Entity\{{ENTITY_NAME}};
use App\Form\{{ENTITY_NAME}}Type;
use App\Repository\{{ENTITY_NAME}}Repository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/{{ENTITY_NAME_LOWER}}')]
class {{ENTITY_NAME}}Controller extends AbstractController
{
    private EntityManagerInterface $em;
    private {{ENTITY_NAME}}Repository $repo;

    public function __construct(EntityManagerInterface $em, {{ENTITY_NAME}}Repository $repo)
    {
        $this->em   = $em;
        $this->repo = $repo;
    }

    #[Route('/', name: '{{ENTITY_NAME_LOWER}}_index', methods: ['GET'])]
    public function index(): Response
    {
        // On récupère tous les objets de type {{ENTITY_NAME}}
        ${{LIST_VAR}} = $this->repo->findAll();

        return $this->render('{{ENTITY_NAME_LOWER}}/index.html.twig', [
            '{{LIST_VAR}}' => ${{LIST_VAR}},
        ]);
    }

    #[Route('/new', name: '{{ENTITY_NAME_LOWER}}_new', methods: ['GET','POST'])]
    public function new(Request $request): Response
    {
        $item = new {{ENTITY_NAME}}();
        $form = $this->createForm({{ENTITY_NAME}}Type::class, $item);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->em->persist($item);
            $this->em->flush();

            return $this->redirectToRoute('{{ENTITY_NAME_LOWER}}_index');
        }

        return $this->render('{{ENTITY_NAME_LOWER}}/create.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/{id}', name: '{{ENTITY_NAME_LOWER}}_show', methods: ['GET'])]
    public function show({{ENTITY_NAME}} $item): Response
    {
        return $this->render('{{ENTITY_NAME_LOWER}}/show.html.twig', [
            '{{ENTITY_NAME_LOWER}}' => $item,
        ]);
    }

    #[Route('/{id}/edit', name: '{{ENTITY_NAME_LOWER}}_edit', methods: ['GET','POST'])]
    public function edit(Request $request, {{ENTITY_NAME}} $item): Response
    {
        $form = $this->createForm({{ENTITY_NAME}}Type::class, $item);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->em->flush();
            return $this->redirectToRoute('{{ENTITY_NAME_LOWER}}_index');
        }

        return $this->render('{{ENTITY_NAME_LOWER}}/edit.html.twig', [
            'form' => $form->createView(),
            '{{ENTITY_NAME_LOWER}}' => $item,
        ]);
    }

    #[Route('/{id}', name: '{{ENTITY_NAME_LOWER}}_delete', methods: ['POST'])]
    public function delete(Request $request, {{ENTITY_NAME}} $item): Response
    {
        if ($this->isCsrfTokenValid('delete'.$item->getId(), $request->request->get('_token'))) {
            $this->em->remove($item);
            $this->em->flush();
        }

        return $this->redirectToRoute('{{ENTITY_NAME_LOWER}}_index');
    }
}
