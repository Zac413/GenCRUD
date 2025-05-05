package com.group.univ.utils;

public class Utils {

    static public String ucfirst(String s) { return s.substring(0,1).toUpperCase() + s.substring(1); }
    static public String lcfirst(String s) { return s.substring(0,1).toLowerCase() + s.substring(1); }
    static public String mapType(String t) {
        if (t == null) return "string";
        switch(t.toLowerCase()) {
            case "varchar": return "string";
            case "integer": return "int";
            case "float": return "float";
            case "date": return "\\DateTimeInterface";
            default: return t;
        }
    }

}
