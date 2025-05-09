<?php

namespace App\Controller;

use App\Entity\{{ENTITY_NAME}};
use App\Form\{{ENTITY_NAME}}Type;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class {{ENTITY_NAME}}Controller extends AbstractController
{
    #[Route('/{{ENTITY_NAME_LOWER}}', name: '{{ENTITY_NAME_LOWER}}')]
    public function index(): Response
    {
        return $this->render('{{ENTITY_NAME_LOWER}}/index.html.twig', [
            'controller_name' => '{{ENTITY_NAME}}Controller',
        ]);
    }

    #[Route('/{{ENTITY_NAME_LOWER}}/create', name: '{{ENTITY_NAME_LOWER}}_create')]
    public function create(Request $request): Response
    {
        $entity = new {{ENTITY_NAME}}();
        $form = $this->createForm({{ENTITY_NAME}}Type::class, $entity);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($entity);
            $entityManager->flush();

            return $this->redirectToRoute('{{ENTITY_NAME_LOWER}}');
        }

        return $this->render('{{ENTITY_NAME_LOWER}}/create.html.twig', [
            'form' => $form->createView(),
        ]);
    }
}