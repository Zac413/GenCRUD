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

class {{ENTITY_NAME}}Controller extends AbstractController
{
    #[Route('/{{ENTITY_NAME_LOWER}}', name: '{{ENTITY_NAME_LOWER}}_index')]
    public function index({{ENTITY_NAME}}Repository $repo): Response
    {
        $list = $repo->findAll();
        return $this->render('{{ENTITY_NAME_LOWER}}/index.html.twig', [
            'list' => $list,
        ]);
    }

    #[Route('/{{ENTITY_NAME_LOWER}}/create', name: '{{ENTITY_NAME_LOWER}}_create')]
    public function create(Request $request, EntityManagerInterface $em): Response
    {
        $entity = new {{ENTITY_NAME}}();
        $form = $this->createForm({{ENTITY_NAME}}Type::class, $entity);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em->persist($entity);
            $em->flush();

            return $this->redirectToRoute('{{ENTITY_NAME_LOWER}}_index');
        }

        return $this->render('{{ENTITY_NAME_LOWER}}/create.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/{{ENTITY_NAME_LOWER}}/{id}/edit', name: '{{ENTITY_NAME_LOWER}}_edit')]
    public function edit(Request $request, {{ENTITY_NAME}}Repository $repo, EntityManagerInterface $em, int $id): Response
    {
        $entity = $repo->find($id);
        if (!$entity) {
            throw $this->createNotFoundException('Non trouvé');
        }
        $form = $this->createForm({{ENTITY_NAME}}Type::class, $entity);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em->flush();
            return $this->redirectToRoute('{{ENTITY_NAME_LOWER}}_index');
        }

        return $this->render('{{ENTITY_NAME_LOWER}}/edit.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/{{ENTITY_NAME_LOWER}}/{id}/delete', name: '{{ENTITY_NAME_LOWER}}_delete', methods: ['POST'])]
    public function delete({{ENTITY_NAME}}Repository $repo, EntityManagerInterface $em, int $id): Response
    {
        $entity = $repo->find($id);
        if ($entity) {
            $em->remove($entity);
            $em->flush();
        }
        return $this->redirectToRoute('{{ENTITY_NAME_LOWER}}_index');
    }
}