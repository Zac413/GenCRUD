package com.group.univ;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
import java.util.*;

public class test {

    static class Field {
        String name, type, size, desc;
        boolean isId;
        Field(String name, String type, String size, String desc, boolean isId) {
            this.name = name; this.type = type; this.size = size; this.desc = desc; this.isId = isId;
        }
    }

    static class Relation {
        String type, from, to, name;
        List<Field> fields = new ArrayList<>();
        Relation(String type, String from, String to, String name) {
            this.type = type; this.from = from; this.to = to; this.name = name;
        }
    }

    static class Entity {
        String name;
        List<Field> fields = new ArrayList<>();
        List<Relation> relations = new ArrayList<>();
        Entity(String name) { this.name = name; }
    }

    public static void main(String[] args) throws Exception {
        Document doc = loadXmlDocument("src/main/resources/com/group/univ/xml/schema.xml");
        Map<String, Entity> entities = parseEntities(doc);
        parseRelations(doc, entities);
        generatePhpFiles(entities);
    }

    // Charge le document XML
    static Document loadXmlDocument(String filename) throws Exception {
        File xmlFile = new File(filename);
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        return doc;
    }

    // Parse les entités et leurs champs/id
    static Map<String, Entity> parseEntities(Document doc) {
        Map<String, Entity> entities = new LinkedHashMap<>();
        NodeList classNodes = doc.getElementsByTagName("class");
        for (int i = 0; i < classNodes.getLength(); i++) {
            Element classElem = (Element) classNodes.item(i);
            String className = ucfirst(classElem.getAttribute("name"));
            Entity entity = new Entity(className);

            // Ids
            NodeList idNodes = classElem.getElementsByTagName("id");
            for (int j = 0; j < idNodes.getLength(); j++) {
                Element idElem = (Element) idNodes.item(j);
                String idName = idElem.getAttribute("name");
                entity.fields.add(new Field(idName, "int", null, null, true));
            }

            // Fields
            NodeList fieldNodes = classElem.getElementsByTagName("field");
            for (int j = 0; j < fieldNodes.getLength(); j++) {
                Element fieldElem = (Element) fieldNodes.item(j);
                String fname = fieldElem.getAttribute("name");
                String ftype = fieldElem.getAttribute("type");
                String fsize = fieldElem.getAttribute("size");
                String fdesc = fieldElem.getAttribute("desc");
                entity.fields.add(new Field(fname, ftype, fsize, fdesc, false));
            }
            entities.put(className, entity);
        }
        return entities;
    }

    // Parse les relations et les rattache aux entités
    static void parseRelations(Document doc, Map<String, Entity> entities) {
        NodeList relNodes = doc.getElementsByTagName("relation");
        for (int i = 0; i < relNodes.getLength(); i++) {
            Element relElem = (Element) relNodes.item(i);
            String type = relElem.getAttribute("type");
            String from = ucfirst(relElem.getAttribute("from"));
            String to   = ucfirst(relElem.getAttribute("to"));
            String name = relElem.getAttribute("name");
            Relation rel = new Relation(type, from, to, name);

            // Champs de la relation (pour les tables de jointure)
            NodeList relFieldNodes = relElem.getElementsByTagName("field");
            for (int j = 0; j < relFieldNodes.getLength(); j++) {
                Element rfElem = (Element) relFieldNodes.item(j);
                String fname = rfElem.getAttribute("name");
                String ftype = rfElem.getAttribute("type");
                rel.fields.add(new Field(fname, ftype, null, null, false));
            }

            Entity fromEntity = entities.get(from);
            if (fromEntity != null) fromEntity.relations.add(rel);
        }
    }

    // Génère les fichiers PHP pour chaque entité
    static void generatePhpFiles(Map<String, Entity> entities) throws IOException {
        File generatedDir = new File("generated");
        if (!generatedDir.exists()) {
            generatedDir.mkdir();
        }
        for (Entity entity : entities.values()) {
            String phpCode = generatePhpCode(entity);
            try (PrintWriter out = new PrintWriter(new File(generatedDir, entity.name + ".php"))) {
                out.print(phpCode);
            }
            System.out.println("Fichier généré : " + entity.name + ".php");
        }
    }

    // Génère le code PHP pour une entité
    static String generatePhpCode(Entity entity) {
        StringBuilder php = new StringBuilder();
        php.append("<?php\n\n");
        php.append("namespace App\\Entity;\n\n");
        php.append("use Doctrine\\ORM\\Mapping as ORM;\n\n");
        php.append("/**\n * @ORM\\Entity\n */\n");
        php.append("class ").append(entity.name).append("\n{\n");

        // Champs
        for (Field f : entity.fields) {
            php.append(generateFieldAnnotation(f));
            php.append("    private $").append(f.name).append(";\n\n");
        }

        // Relations
        for (Relation r : entity.relations) {
            php.append(generateRelationAnnotation(r, entity.name));
        }

        php.append("}\n");
        return php.toString();
    }

    // Génère les annotations pour un champ
    static String generateFieldAnnotation(Field f) {
        StringBuilder sb = new StringBuilder();
        if (f.isId) {
            sb.append("    /**\n")
                    .append("     * @ORM\\Id\n")
                    .append("     * @ORM\\GeneratedValue\n")
                    .append("     * @ORM\\Column(type=\"integer\")\n")
                    .append("     */\n");
        } else {
            sb.append("    /**\n")
                    .append("     * @ORM\\Column(type=\"")
                    .append(mapType(f.type))
                    .append("\"");
            if (f.size != null && !f.size.isEmpty())
                sb.append(", length=").append(f.size);
            sb.append(")\n");
            if (f.desc != null && !f.desc.isEmpty())
                sb.append("     * ").append(f.desc).append("\n");
            sb.append("     */\n");
        }
        return sb.toString();
    }

    // Génère les annotations pour une relation
    static String generateRelationAnnotation(Relation r, String entityName) {
        StringBuilder sb = new StringBuilder();
        if (r.type.equalsIgnoreCase("one-to-one")) {
            sb.append("    /**\n")
                    .append("     * @ORM\\OneToOne(targetEntity=\"").append(r.to).append("\")\n")
                    .append("     * @ORM\\JoinColumn(name=\"").append(r.name).append("\", referencedColumnName=\"id\")\n")
                    .append("     */\n")
                    .append("    private $").append(lcfirst(r.to)).append(";\n\n");
        }
        if (r.type.equalsIgnoreCase("one-to-many")) {
            sb.append("    /**\n")
                    .append("     * @ORM\\OneToMany(targetEntity=\"").append(r.to).append("\", mappedBy=\"").append(lcfirst(entityName)).append("\")\n")
                    .append("     */\n")
                    .append("    private $").append(lcfirst(r.to)).append("s;\n\n");
        }
        // Tu peux ajouter d'autres types ici (many-to-one, many-to-many) si nécessaire
        return sb.toString();
    }

    static String ucfirst(String s) { return s.substring(0,1).toUpperCase() + s.substring(1); }
    static String lcfirst(String s) { return s.substring(0,1).toLowerCase() + s.substring(1); }
    static String mapType(String t) {
        if (t == null) return "string";
        switch(t.toLowerCase()) {
            case "varchar": return "string";
            case "int": return "integer";
            case "float": return "float";
            case "date": return "date";
            default: return t;
        }
    }
}