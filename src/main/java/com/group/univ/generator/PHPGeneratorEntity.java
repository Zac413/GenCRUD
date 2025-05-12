package com.group.univ.generator;

import com.group.univ.model.Entity;
import com.group.univ.model.Field;
import com.group.univ.model.Relation;
import com.group.univ.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class PHPGeneratorEntity {

    public static String PHP_TEMPLATE_PATH = "src/main/resources/com/group/univ/php-template/entity/";
    public static String ENTITY_HEADER_PHP_TPL = PHP_TEMPLATE_PATH+"entity-header.php.tpl";
    public static String ENTITY_HEADER_ENTITY_PHP_TPL = PHP_TEMPLATE_PATH+"entity-header-entity.php.tpl";
    public static String ENTITY_HEADER_LIST_PHP_TPL = PHP_TEMPLATE_PATH+"entity-header-list.php.tpl";

    public static String ENTITY_FOOTER_PHP_TPL = PHP_TEMPLATE_PATH+"entity-footer.php.tpl";
    public static String ENTITY_FIELD_ID_PHP_TPL = PHP_TEMPLATE_PATH+"entity-field-id.php.tpl";
    public static String ENTITY_FIELD_PHP_TPL = PHP_TEMPLATE_PATH+"entity-field.php.tpl";

    public static String ENTITY_RELATION_ONETOONE_PHP_TPL = PHP_TEMPLATE_PATH+"entity-relation-OneToOne.php.tpl";
    public static String ENTITY_RELATION_ONETOMANY_PHP_TPL = PHP_TEMPLATE_PATH+"entity-relation-OneToMany.php.tpl";

    public static String ENTITY_GETTER_PHP_TPL = PHP_TEMPLATE_PATH+"entity-getter.php.tpl";
    public static String ENTITY_SETTER_PHP_TPL = PHP_TEMPLATE_PATH+"entity-setter.php.tpl";
    public static String ENTITY_LIST_ADD_REMOVE_PHP_TPL = PHP_TEMPLATE_PATH+"entity-add-remove-list.php.tpl";

    public static String ENTITY_CONSTRUCTOR_PHP_TPL = PHP_TEMPLATE_PATH+"entity-constructor.php.tpl";
    public static String ENTITY_CONSTRUCTOR_FOOTER_PHP_TPL = PHP_TEMPLATE_PATH+"entity-constructor-footer.php.tpl";
    public static String ENTITY_CONSTRUCTOR_LIST_PHP_TPL = PHP_TEMPLATE_PATH+"entity-constructor-list.php.tpl";


    // Génère les fichiers PHP pour chaque entité
    public void generatePhpFiles(Map<String, Entity> entities) throws IOException {
        File generatedDir = new File("symfony/src/Entity");
        if (!generatedDir.exists()) {
            generatedDir.mkdir();
        }
        for (Entity entity : entities.values()) {
            String phpCode = generatePhpCode(entity);
            try (PrintWriter out = new PrintWriter(new File(generatedDir, entity.getName() + ".php"))) {
                out.print(phpCode);
            }
            System.out.println("Fichier généré : " + entity.getName() + ".php");
        }
    }



    // Génère le code PHP pour une entité
    public String generatePhpCode(Entity entity) {
        StringBuilder php = new StringBuilder();
        // Header
        php.append(generateHeaderPhpCode(entity));

        // Fields
        for (Field f : entity.getFields()) {
            php.append(generateFieldPhpCode(f));
        }

        // Relations
        for (Relation r : entity.getRelations()) {
            php.append(generateRelationPhpCode(r, entity.getName()));
        }

        // Constructor
        php.append(generateConstructorPhpCode(entity));

        // Getters and Setters
        for (Field f : entity.getFields()) {
            php.append(generateGetterPhpCode(f.getName(), f.getType()));
            if(!f.isId()){
                php.append(generateSetterPhpCode(f.getName(), f.getType()));
            }
        }

        // Getters and Setters for relations
        for (Relation r : entity.getRelations()) {
            String type = "";
            String name = "";
            if(r.getType().equalsIgnoreCase("one-to-one")) {
                type = Utils.mapType(r.getTo());
                name = r.getTo();
            } else if (r.getType().equalsIgnoreCase("one-to-many")) {
                type = "Collection";
                name = r.getTo()+"s";
            }
            php.append(generateGetterPhpCode(name, type));
            php.append(generateSetterPhpCode(name, type));
            if(r.getType().equalsIgnoreCase("one-to-many")) {
                php.append(generateListAddRemovePhpCode(r.getTo(), type));
            }
        }


        // Footer
        php.append(generateFooterPhpCode());
        return php.toString();
    }


    public String generateHeaderPhpCode(Entity entity) {
        String template;
        String template_list;
        String template_entity;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ENTITY_HEADER_PHP_TPL)));
            template_list = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ENTITY_HEADER_LIST_PHP_TPL)));
            template_entity = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ENTITY_HEADER_ENTITY_PHP_TPL)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }
        StringBuilder imports = new StringBuilder();
        imports.append("");
        if(entity.getRelations() != null) {
            for (Relation relation : entity.getRelations()) {
                if (relation.getType().equalsIgnoreCase("one-to-many")) {
                    imports.append(template_list);
                }
                imports.append(template_entity.replace("{{RELATION_TO}}", relation.getTo()));
            }
        }
        template = template.replace("{{IMPORTS}}", imports.toString());

        template = template.replace("{{CLASS_NAME}}", entity.getName());
        return template;
    }

    public String generateConstructorPhpCode(Entity entity) {
        StringBuilder template = new StringBuilder();

        String template_header;
        String template_list;
        String template_footer;
        try {
            template_header = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ENTITY_CONSTRUCTOR_PHP_TPL)));
            template_list = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ENTITY_CONSTRUCTOR_LIST_PHP_TPL)));
            template_footer = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ENTITY_CONSTRUCTOR_FOOTER_PHP_TPL)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template.append(template_header);

        boolean hasManyToOne = false;

        for(Relation relation: entity.getRelations()) {
            if(relation.getType().equalsIgnoreCase("one-to-many")) {
                template_list = template_list.replace("{{RELATION_to}}", Utils.lcfirst(relation.getTo()));
                hasManyToOne = true;
            }
        }
        if(hasManyToOne) {
            template.append(template_list);
        }

        template.append(template_footer);
        return template.toString();
    }

    public String generateFooterPhpCode() {
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ENTITY_FOOTER_PHP_TPL)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        return template;
    }

    public String generateFieldPhpCode(Field field) {
        String template;
        if(field.isId()) {
            try {
                template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ENTITY_FIELD_ID_PHP_TPL)));
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
            }
            template = template.replace("{{FIELD_NAME}}", field.getName());
            template = template.replace("{{FIELD_TYPE}}", Utils.mapType(field.getType()));

        }else {
            try {
                template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ENTITY_FIELD_PHP_TPL)));
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
            }
            template = template.replace("{{FIELD_NAME}}", field.getName());
            template = template.replace("{{FIELD_TYPE_ORM}}", Utils.mapType(field.getType()));
            template = template.replace("{{FIELD_TYPE}}", Utils.mapTypeORM(field.getType()));
            template = template.replace("{{FIELD_SIZE}}", field.getSize());
            template = template.replace("{{FIELD_DESC}}", field.getDesc());
        }
        return template;
    }

    public String generateRelationPhpCode(Relation relation, String entityName) {
        String template;
        String type;
        try {
            if(relation.getType().equalsIgnoreCase("one-to-one")) {
                template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ENTITY_RELATION_ONETOONE_PHP_TPL)));
                type = Utils.mapType(relation.getTo());
            } else if (relation.getType().equalsIgnoreCase("one-to-many")) {
                template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ENTITY_RELATION_ONETOMANY_PHP_TPL)));
                type = "Collection";
            } else {
                throw new IllegalArgumentException("Type de relation non supporté : " + relation.getType());
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{RELATION_TO}}", relation.getTo());
        template = template.replace("{{RELATION_to}}", Utils.lcfirst(relation.getTo()));
        template = template.replace("{{TWO_FIRST_LETTER}}", Utils.lcfirst(Utils.toCamelCase(relation.getTo()).substring(0, 2)));
        template = template.replace("{{RELATION_FROM}}", relation.getFrom());
        template = template.replace("{{RELATION_NAME}}", relation.getName());
        template = template.replace("{{ENTITY_NAME}}", Utils.lcfirst(entityName));
        template = template.replace("{{FIELD_TYPE}}", type);

        return template;
    }

    public String generateGetterPhpCode(String name, String type) {
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ENTITY_GETTER_PHP_TPL)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{NAME}}", Utils.lcfirst(name));
        template = template.replace("{{NAME_CAMEL}}", Utils.toCamelCase(name));
        template = template.replace("{{TYPE}}", Utils.mapType(type));
        return template;
    }

    public String generateSetterPhpCode(String name, String type) {
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ENTITY_SETTER_PHP_TPL)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{NAME}}", Utils.lcfirst(name));
        template = template.replace("{{NAME_CAMEL}}", Utils.toCamelCase(name));
        template = template.replace("{{TYPE}}", Utils.mapType(type));
        return template;
    }

    public String generateListAddRemovePhpCode(String name, String type) {
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ENTITY_LIST_ADD_REMOVE_PHP_TPL)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{NAME}}", name);
        template = template.replace("{{name}}", Utils.lcfirst(name));
        template = template.replace("{{NAME_CAMEL}}", Utils.toCamelCase(name));
        template = template.replace("{{TYPE}}", Utils.mapType(type));
        return template;
    }


}
