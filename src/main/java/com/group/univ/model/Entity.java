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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Entity: ").append(name).append("\n");

        sb.append("  Fields:\n");
        for (Field field : fields) {
            sb.append("    - ").append(field.toString()).append("\n");
        }

        sb.append("  Relations:\n");
        for (Relation relation : relations) {
            sb.append("    - ").append(relation.toString()).append("\n");
        }

        return sb.toString();
    }

}
