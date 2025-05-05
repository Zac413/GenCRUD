package com.group.univ.model;

public class Field {
    String name, type, size, desc;
    boolean isId;

    public Field(String name, String type, String size, String desc, boolean isId) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.desc = desc;
        this.isId = isId;
        if(size == null || size.isEmpty()){
            this.size = "255";
        }
        if(desc == null || desc.isEmpty()){
            this.desc = "";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isId() {
        return isId;
    }

    public void setId(boolean id) {
        isId = id;
    }
}
