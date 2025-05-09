    #[ORM\OneToMany(targetEntity: {{RELATION_TO}}::class, mappedBy: '{{ENTITY_NAME_LOWER}}')]
    private Collection ${{RELATION_to}}s;
