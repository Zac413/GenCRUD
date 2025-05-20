package com.group.univ.generator;

import com.group.univ.model.Entity;
import com.group.univ.model.Field;
import com.group.univ.model.Relation;
import com.group.univ.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class PHPGeneratorEntity {

    public static final String PHP_TEMPLATE_PATH             = "src/main/resources/com/group/univ/php-template/entity/";
    public static final String ENTITY_HEADER_PHP_TPL         = PHP_TEMPLATE_PATH + "entity-header.php.tpl";
    public static final String ENTITY_HEADER_ENTITY_PHP_TPL  = PHP_TEMPLATE_PATH + "entity-header-entity.php.tpl";
    public static final String ENTITY_HEADER_LIST_PHP_TPL    = PHP_TEMPLATE_PATH + "entity-header-list.php.tpl";

    public static final String ENTITY_FIELD_ID_PHP_TPL       = PHP_TEMPLATE_PATH + "entity-field-id.php.tpl";
    public static final String ENTITY_FIELD_PHP_TPL          = PHP_TEMPLATE_PATH + "entity-field.php.tpl";

    public static final String ENTITY_RELATION_ONETOONE_PHP_TPL  = PHP_TEMPLATE_PATH + "entity-relation-OneToOne.php.tpl";
    public static final String ENTITY_RELATION_ONETOMANY_PHP_TPL = PHP_TEMPLATE_PATH + "entity-relation-OneToMany.php.tpl";
    public static final String ENTITY_RELATION_MANYTOONE_PHP_TPL = PHP_TEMPLATE_PATH + "entity-relation-ManyToOne.php.tpl";
    public static final String ENTITY_RELATION_MANYTOMANY_PHP_TPL = PHP_TEMPLATE_PATH + "entity-relation-ManyToMany.php.tpl";
    public static final String ENTITY_RELATION_MANYTOMANY_hasTo_PHP_TPL = PHP_TEMPLATE_PATH + "entity-relation-ManyToMany-hasTo.php.tpl";

    public static final String ENTITY_CONSTRUCTOR_PHP_TPL        = PHP_TEMPLATE_PATH + "entity-constructor.php.tpl";
    public static final String ENTITY_CONSTRUCTOR_LIST_PHP_TPL   = PHP_TEMPLATE_PATH + "entity-constructor-list.php.tpl";
    public static final String ENTITY_CONSTRUCTOR_FOOTER_PHP_TPL = PHP_TEMPLATE_PATH + "entity-constructor-footer.php.tpl";

    public static final String ENTITY_GETTER_PHP_TPL              = PHP_TEMPLATE_PATH + "entity-getter.php.tpl";
    public static final String ENTITY_SETTER_PHP_TPL              = PHP_TEMPLATE_PATH + "entity-setter.php.tpl";
    public static final String ENTITY_LIST_ADD_REMOVE_PHP_TPL     = PHP_TEMPLATE_PATH + "entity-add-remove-list.php.tpl";

    public static final String ENTITY_FOOTER_PHP_TPL           = PHP_TEMPLATE_PATH + "entity-footer.php.tpl";
    public static final String ENTITY_TOSTRING_PHP_TPL         = PHP_TEMPLATE_PATH + "entity-tostring.php.tpl";

    /**
     * Génère les fichiers PHP pour chaque entité.
     */
    public void generatePhpFiles(Map<String, Entity> entities) throws IOException {
        File generatedDir = new File("symfony/src/Entity");
        if (!generatedDir.exists()) {
            generatedDir.mkdir();
        }
        for (Entity entity : entities.values()) {
            String phpCode = generatePhpCode(entity);
            File out = new File(generatedDir, entity.getName() + ".php");
            try (PrintWriter writer = new PrintWriter(out)) {
                writer.print(phpCode);
            }
            System.out.println("Fichier généré : " + out.getName());
        }
    }

    /**
     * Génère le code PHP complet pour une entité.
     */
    public String generatePhpCode(Entity entity) {
        StringBuilder php = new StringBuilder();
        // header & imports
        php.append(generateHeaderPhpCode(entity));
        // fields
        entity.getFields().forEach(f -> php.append(generateFieldPhpCode(f)));
        // relations
        entity.getRelations().forEach(r -> php.append(generateRelationPhpCode(r, entity.getName())));
        // constructor
        php.append(generateConstructorPhpCode(entity));
        // getters & setters
        entity.getFields().forEach(f -> {
            php.append(generateGetterPhpCode(f.getName(), f.getType()));
            if (!f.isId()) {
                php.append(generateSetterPhpCode(f.getName(), f.getType()));
            }
        });
        entity.getRelations().forEach(r -> {
            String type="";
            String name="";
            if(r.getType().equalsIgnoreCase("one-to-one")){
                type = Utils.mapTypePhp(r.getTo());
                name = r.getTo();
            }else if(r.getType().equalsIgnoreCase("one-to-many")){
                type = "Collection";
                name = r.getTo()+"s";
            }else if(r.getType().equalsIgnoreCase("many-to-one")){
                type = Utils.mapTypePhp(r.getTo());
                name = r.getTo();
            }else if(r.getType().equalsIgnoreCase("many-to-many")){
                type = "Collection";
                name = r.getTo()+"s";
            }
            php.append(generateGetterPhpCode(name, type));
            php.append(generateSetterPhpCode(name, type));
            if (r.getType().equalsIgnoreCase("one-to-many")||r.getType().equalsIgnoreCase("many-to-many")) {
                php.append(generateListAddRemovePhpCode(entity.getName(),r.getTo(), type));
            }
        });
        // toString method
        php.append(generateToString(entity));
        // footer
        php.append(generateFooterPhpCode());
        return php.toString();
    }

    private String generateHeaderPhpCode(Entity entity) {
        try {
            String tpl       = Files.readString(Paths.get(ENTITY_HEADER_PHP_TPL));
            String tplList   = Files.readString(Paths.get(ENTITY_HEADER_LIST_PHP_TPL));
            String tplEntity = Files.readString(Paths.get(ENTITY_HEADER_ENTITY_PHP_TPL));
            StringBuilder imports = new StringBuilder();
            if (entity.getRelations() != null) {
                for (Relation r : entity.getRelations()) {
                    if (r.getType().equalsIgnoreCase("one-to-many")||r.getType().equalsIgnoreCase("many-to-many")) {
                        imports.append(tplList);
                    }
                    imports.append(tplEntity.replace("{{RELATION_TO}}", r.getTo()));
                }
            }
            return tpl.replace("{{IMPORTS}}", imports.toString())
                    .replace("{{CLASS_NAME}}", entity.getName());
        } catch (IOException e) {
            throw new RuntimeException("Erreur lecture header template : " + e.getMessage(), e);
        }
    }

    private String generateFieldPhpCode(Field field) {
        try {
            String path = field.isId() ? ENTITY_FIELD_ID_PHP_TPL : ENTITY_FIELD_PHP_TPL;
            String tpl = Files.readString(Paths.get(path));
            if (field.isId()) {
                return tpl.replace("{{FIELD_NAME}}", field.getName())
                        .replace("{{FIELD_TYPE}}", Utils.mapType(field.getType()));
            } else {
                return tpl.replace("{{FIELD_NAME}}", field.getName())
                        .replace("{{FIELD_TYPE_ORM}}", Utils.mapType(field.getType()))
                        .replace("{{FIELD_TYPE}}", Utils.mapTypePhp(field.getType()))
                        .replace("{{FIELD_SIZE}}", field.getSize())
                        .replace("{{FIELD_DESC}}", field.getDesc());
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lecture field template : " + e.getMessage(), e);
        }
    }

    private String generateRelationPhpCode(Relation r, String entityName) {
        try {
            String tpl = "", type="";
            if (r.getType().equalsIgnoreCase("one-to-one")) {
                tpl  = Files.readString(Paths.get(ENTITY_RELATION_ONETOONE_PHP_TPL));
                type = Utils.mapTypePhp(r.getTo());
            } else if (r.getType().equalsIgnoreCase("one-to-many")) {
                tpl  = Files.readString(Paths.get(ENTITY_RELATION_ONETOMANY_PHP_TPL));
                type = "Collection";
            } else if (r.getType().equalsIgnoreCase("many-to-one")) {
                tpl  = Files.readString(Paths.get(ENTITY_RELATION_MANYTOONE_PHP_TPL));
                type = Utils.mapTypePhp(r.getTo());
            } else if (r.getType().equalsIgnoreCase("many-to-many")) {
                if (r.getHasTo()){
                    tpl  = Files.readString(Paths.get(ENTITY_RELATION_MANYTOMANY_hasTo_PHP_TPL));
                } else {
                    tpl  = Files.readString(Paths.get(ENTITY_RELATION_MANYTOMANY_PHP_TPL));
                }
                type = "Collection";
            }
            return tpl.replace("{{RELATION_TO}}", r.getTo())
                    .replace("{{RELATION_to}}", Utils.lcfirst(r.getTo()))
                    .replace("{{RELATION_FROM}}", r.getFrom())
                    .replace("{{TWO_FIRST_LETTER}}", Utils.lcfirst(Utils.toCamelCase(r.getTo()).substring(0,2)))
                    .replace("{{TWO_LETTER_ENTITY}}", Utils.lcfirst(Utils.toCamelCase(entityName).substring(0,2)))
                    .replace("{{RELATION_NAME}}", r.getName())
                    .replace("{{ENTITY_NAME}}", Utils.lcfirst(entityName))
                    .replace("{{FIELD_TYPE}}", type);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lecture relation template : " + e.getMessage(), e);
        }
    }

    private String generateConstructorPhpCode(Entity entity) {
        try {
            String header = Files.readString(Paths.get(ENTITY_CONSTRUCTOR_PHP_TPL));
            String list   = Files.readString(Paths.get(ENTITY_CONSTRUCTOR_LIST_PHP_TPL));
            String footer = Files.readString(Paths.get(ENTITY_CONSTRUCTOR_FOOTER_PHP_TPL));
            StringBuilder sb = new StringBuilder(header);
            for (Relation r : entity.getRelations()) {
                if (r.getType().equalsIgnoreCase("one-to-many")||r.getType().equalsIgnoreCase("many-to-many")) {
                    sb.append(list.replace("{{RELATION_to}}", Utils.lcfirst(r.getTo())));
                    break;
                }
            }
            sb.append(footer);
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("Erreur lecture constructor template : " + e.getMessage(), e);
        }
    }

    private String generateGetterPhpCode(String name, String type) {
        try {
            String tpl = Files.readString(Paths.get(ENTITY_GETTER_PHP_TPL));
            return tpl.replace("{{NAME}}", Utils.lcfirst(name))
                    .replace("{{NAME_CAMEL}}", Utils.toCamelCase(name))
                    .replace("{{TYPE}}", Utils.mapTypePhp(type));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lecture getter template : " + e.getMessage(), e);
        }
    }

    private String generateSetterPhpCode(String name, String type) {
        try {
            String tpl = Files.readString(Paths.get(ENTITY_SETTER_PHP_TPL));
            return tpl.replace("{{NAME}}", Utils.lcfirst(name))
                    .replace("{{NAME_CAMEL}}", Utils.toCamelCase(name))
                    .replace("{{TYPE}}", Utils.mapTypePhp(type));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lecture setter template : " + e.getMessage(), e);
        }
    }

    private String generateListAddRemovePhpCode(String className,String name, String type) {
        try {
            String tpl = Files.readString(Paths.get(ENTITY_LIST_ADD_REMOVE_PHP_TPL));
            return tpl.replace("{{NAME}}", name)
                    .replace("{{name}}", Utils.lcfirst(name))
                    .replace("{{NAME_CAMEL}}", Utils.toCamelCase(name))
                    .replace("{{TYPE}}", Utils.mapTypePhp(type))
                    .replace("{{CLASS_NAME}}", Utils.toCamelCase(className));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lecture list add/remove template : " + e.getMessage(), e);
        }
    }

    /**
     * Génère dynamiquement la méthode __toString() selon l'entité.
     */
    private String generateToString(Entity entity) {
        try {
            String tpl  = Files.readString(Paths.get(ENTITY_TOSTRING_PHP_TPL));
            String expr;
            List<Field> fields = entity.getFields();
            StringBuilder sb = new StringBuilder();
            for(Field field : entity.getFields()){
                if(field.getType().equalsIgnoreCase("date")){
                    sb.append("$this->").append(field.getName()).append("->format('Y-m-d H:i:s')").append(".' '.");
                }else{
                    sb.append("$this->").append(field.getName()).append(".' '.");
                }

            }

            for(Relation relation : entity.getRelations()) {
                if(relation.getType().equalsIgnoreCase("one-to-many")) {
                    //TODO
                } else if(relation.getType().equalsIgnoreCase("one-to-one")){
                    sb.append("$this->").append(Utils.lcfirst(relation.getTo())).append("->__toString()").append(".' '.");
                } else if(relation.getType().equalsIgnoreCase("many-to-one")) {
                    //TODO
                } else if(relation.getType().equalsIgnoreCase("many-to-many")) {
                    //TODO
                }
            }

            return tpl.replace("{{TOSTRING_EXPRESSION}}", sb.toString());
        } catch (IOException e) {
            throw new RuntimeException("Erreur génération toString: " + e.getMessage(), e);
        }
    }

    /**
     * Lit simplement le template de footer (sans __toString).
     */
    private String generateFooterPhpCode() {
        try {
            return Files.readString(Paths.get(ENTITY_FOOTER_PHP_TPL));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lecture footer template : " + e.getMessage(), e);
        }
    }
}
