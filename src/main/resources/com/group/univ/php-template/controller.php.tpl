<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class {{ENTITY_NAME}}Controller extends AbstractController
{
    #[Route('/{{ENTITY_NAME_LOWER}}, name: '{{ENTITY_NAME_LOWER}}')]
    public function index(): Response
    {
        return $this->render('{{ENTITY_NAME_LOWER}}/index.html.twig', [
        'controller_name' => '{{ENTITY_NAME}}Controller',
        ]);
    } //testtest
}