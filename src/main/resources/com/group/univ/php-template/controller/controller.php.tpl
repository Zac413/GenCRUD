<?php

namespace App\Controller;

{{IMPORT_ONETOMANY}}
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
    public function __construct(
        private EntityManagerInterface $em,
        private {{ENTITY_NAME}}Repository $repo
    ) {}

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
        {{FOREACH_ONETOMANY}}
            $this->em->persist($entity);
            $this->em->flush();

            return $this->redirectToRoute('{{ENTITY_NAME_LOWER}}');
        }

        return $this->render('{{ENTITY_NAME_LOWER}}/create.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/{{ENTITY_NAME_LOWER}}/edit/{id}', name: '{{ENTITY_NAME_LOWER}}_edit')]
    public function edit(Request $request, int $id): Response
    {
        ${{ENTITY_NAME_LOWER}} = $this->repo->find($id);
        if (!${{ENTITY_NAME_LOWER}}) {
            throw $this->createNotFoundException('{{ENTITY_NAME_LOWER}} not found');
        }

            {{EDIT_ONETOMANY}}

        $form = $this->createForm({{ENTITY_NAME}}Type::class, ${{ENTITY_NAME_LOWER}});
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->em->flush();

            return $this->redirectToRoute('{{ENTITY_NAME_LOWER}}');
        }

        return $this->render('{{ENTITY_NAME_LOWER}}/edit.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/{{ENTITY_NAME_LOWER}}/delete/{id}', name: '{{ENTITY_NAME_LOWER}}_delete', methods: ['POST'])]
    public function delete(int $id): Response
    {
        ${{ENTITY_NAME_LOWER}} = $this->repo->find($id);
        if (${{ENTITY_NAME_LOWER}}) {
            $this->em->remove(${{ENTITY_NAME_LOWER}});
            $this->em->flush();
        }

        return $this->redirectToRoute('{{ENTITY_NAME_LOWER}}');
    }
}
