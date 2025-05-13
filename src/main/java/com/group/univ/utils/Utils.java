package com.group.univ.utils;

public class Utils {

    public static String ucfirst(String s) { return s.substring(0,1).toUpperCase() + s.substring(1); }
    public static String lcfirst(String s) { return s.substring(0,1).toLowerCase() + s.substring(1); }

    public static String mapType(String t) {
        if (t == null) return "string";
        switch(t.toLowerCase()) {
            case "varchar": return "string";
            case "integer": return "int";
            case "float": return "float";
            case "date": return "datetime";
            default: return t;
        }
    }

    public static String mapTypePhp(String t) {
        if (t == null) return "string";
        switch(t.toLowerCase()) {
            case "varchar": return "string";
            case "integer": return "int";
            case "float": return "float";
            case "date": return "\\DateTime";
            default: return t;
        }
    }

    public static String mapTypeORM(String t) {
        if (t == null) return "string";
        switch(t.toLowerCase()) {
            case "varchar": return "string";
            case "integer": return "int";
            case "float": return "float";
            case "date": return "\\DateTime";
            default: return t;
        }
    }


    public static String toCamelCase(String input) {
        String[] parts = input.split("_");
        StringBuilder sb = new StringBuilder();

        for (String part : parts) {
            if (part.length() > 0) {
                sb.append(Character.toUpperCase(part.charAt(0)));
                sb.append(part.substring(1));
            }
        }

        return sb.toString();
    }

}
