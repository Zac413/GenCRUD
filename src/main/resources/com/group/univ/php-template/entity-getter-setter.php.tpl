
    public function get{{NAME}}(): ?{{TYPE}}
    {
        return $this->{{NAME}};
    }

    public function set{{NAME}}({{TYPE}} ${{NAME}}): self
    {
        $this->{{NAME}} = ${{NAME}};
        return $this;
    }