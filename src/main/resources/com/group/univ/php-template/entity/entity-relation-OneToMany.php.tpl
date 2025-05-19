
    #[ORM\OneToMany(targetEntity: {{RELATION_TO}}::class, mappedBy: '{{ENTITY_NAME}}', cascade: ['persist'], orphanRemoval: true)]
    private ?{{FIELD_TYPE}} ${{RELATION_to}}s;
