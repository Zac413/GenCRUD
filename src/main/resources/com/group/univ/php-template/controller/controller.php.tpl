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
    private EntityManagerInterface $em;
    private {{ENTITY_NAME}}Repository $repo;

    public function __construct(EntityManagerInterface $em, {{ENTITY_NAME}}Repository $repo)
    {
        $this->em   = $em;
        $this->repo = $repo;
    }

    #[Route('/{{ENTITY_NAME_LOWER}}', name: '{{ENTITY_NAME_LOWER}}')]
    public function index(): Response
    {
        $list = $this->repo->findAll();
        return $this->render('{{ENTITY_NAME_LOWER}}/index.html.twig', [
            '{{ENTITY_NAME_LOWER}}s' => $list,
        ]);
    }

    #[Route('/{{ENTITY_NAME_LOWER}}/create', name: '{{ENTITY_NAME_LOWER}}_create')]
    public function create(Request $request): Response
    {
        $entity = new {{ENTITY_NAME}}();
        $form   = $this->createForm({{ENTITY_NAME}}Type::class, $entity);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $this->em->persist($entity);
            $this->em->flush();
            return $this->redirectToRoute('{{ENTITY_NAME_LOWER}}');
        }

        return $this->render('{{ENTITY_NAME_LOWER}}/create.html.twig', [
            'form' => $form->createView(),
        ]);
    }
}
