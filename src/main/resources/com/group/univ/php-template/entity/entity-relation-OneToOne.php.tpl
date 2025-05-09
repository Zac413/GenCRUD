    #[ORM\OneToOne(targetEntity: {{RELATION_TO}}::class)]
    #[ORM\JoinColumn(name: '{{RELATION_NAME}}', referencedColumnName: 'id')]
    private ?{{RELATION_TO}} ${{RELATION_to}} = null;
