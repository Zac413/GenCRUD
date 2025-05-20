
        foreach ($entity->get{{ONETOMANY_TO}}s() as ${{ONETOMANY_to}}) {
            ${{ONETOMANY_to}}->set{{ONETOMANY_FROM}}($entity);
        }