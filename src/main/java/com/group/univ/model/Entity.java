package com.group.univ.model;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    String name;
    List<Field> fields = new ArrayList<>();
    List<Relation> relations = new ArrayList<>();

    public Entity(String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }
}
