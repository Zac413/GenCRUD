package com.group.univ.model;

import java.util.ArrayList;
import java.util.List;

public class Relation {
    String type, from, to, name;
    List<Field> fields = new ArrayList<>();

    public Relation(String type, String from, String to, String name) {
        this.type = type; this.from = from; this.to = to; this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type).append(" ").append(from).append(" -> ").append(to);
        sb.append(" [name=").append(name).append("]");

        if (!fields.isEmpty()) {
            sb.append(" {fields: ");
            for (Field f : fields) {
                sb.append(f.toString()).append("; ");
            }
            sb.append("}");
        }

        return sb.toString();
    }

}
