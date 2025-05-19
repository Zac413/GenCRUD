
    public function add{{NAME_CAMEL}}({{NAME}} ${{name}}): self
    {
        if (!$this->{{name}}s->contains(${{name}}))
        {
            $this->{{name}}s[] = ${{name}};
            ${{name}}->set{{CLASS_NAME}}($this);


        }
        return $this;
    }

    public function remove{{NAME_CAMEL}}({{NAME}} ${{name}}): self
    {
        $this->{{name}}s->removeElement(${{name}});
        ${{name}}->set{{CLASS_NAME}}(null);
        return $this;
    }
