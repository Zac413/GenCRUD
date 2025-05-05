
    /**
    * @ORM\OneToOne(targetEntity="{{RELATION_TO}}")
    * @ORM\JoinColumn(name="{{RELATION_NAME}}", referencedColumnName="id")
    */
    private ${{RELATION_to}};
