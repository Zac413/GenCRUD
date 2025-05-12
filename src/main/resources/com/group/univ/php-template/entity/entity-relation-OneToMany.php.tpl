
    #[ORM\OneToMany(targetEntity: {{RELATION_TO}}::class, mappedBy: '{{ENTITY_NAME}}')]
    private ?{{FIELD_TYPE}} ${{RELATION_to}}s = null;
