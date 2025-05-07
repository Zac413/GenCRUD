
    /**
    * @ORM\OneToOne(targetEntity="{{RELATION_TO}}")
    * @ORM\JoinColumn(name="{{RELATION_NAME}}", referencedColumnName="id")
    */
    private ?{{FIELD_TYPE}} ${{RELATION_to}};
