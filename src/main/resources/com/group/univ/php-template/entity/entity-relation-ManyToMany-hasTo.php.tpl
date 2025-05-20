
        #[ORM\ManyToMany(targetEntity: {{RELATION_TO}}::class, mappedBy: '{{ENTITY_NAME}}s')]
        private Collection ${{RELATION_to}}s;
