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
            String type = r.getType().equalsIgnoreCase("one-to-one")
                    ? Utils.mapTypePhp(r.getTo())
                    : "Collection";
            String name = r.getType().equalsIgnoreCase("one-to-many")
                    ? r.getTo() + "s"
                    : r.getTo();
            php.append(generateGetterPhpCode(name, type));
            php.append(generateSetterPhpCode(name, type));
            if (r.getType().equalsIgnoreCase("one-to-many")) {
                php.append(generateListAddRemovePhpCode(r.getTo(), type));
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
                    if (r.getType().equalsIgnoreCase("one-to-many")) {
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
            String tpl, type;
            if (r.getType().equalsIgnoreCase("one-to-one")) {
                tpl  = Files.readString(Paths.get(ENTITY_RELATION_ONETOONE_PHP_TPL));
                type = Utils.mapTypePhp(r.getTo());
            } else {
                tpl  = Files.readString(Paths.get(ENTITY_RELATION_ONETOMANY_PHP_TPL));
                type = "Collection";
            }
            return tpl.replace("{{RELATION_TO}}", r.getTo())
                    .replace("{{RELATION_to}}", Utils.lcfirst(r.getTo()))
                    .replace("{{RELATION_FROM}}", r.getFrom())
                    .replace("{{TWO_FIRST_LETTER}}", Utils.lcfirst(Utils.toCamelCase(r.getTo()).substring(0,2)))
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
                if (r.getType().equalsIgnoreCase("one-to-many")) {
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

    private String generateListAddRemovePhpCode(String name, String type) {
        try {
            String tpl = Files.readString(Paths.get(ENTITY_LIST_ADD_REMOVE_PHP_TPL));
            return tpl.replace("{{NAME}}", name)
                    .replace("{{name}}", Utils.lcfirst(name))
                    .replace("{{NAME_CAMEL}}", Utils.toCamelCase(name))
                    .replace("{{TYPE}}", Utils.mapTypePhp(type));
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
                    // boucler sur tout les produits
//                    implode(', ', array_map(fn($p) => (string) $p, $this->produits->toArray()));


                } else if(relation.getType().equalsIgnoreCase("one-to-one")){
                    sb.append("$this->").append(Utils.lcfirst(relation.getTo())).append("->__toString()").append(".' '.");
                }
            }


//            switch (entity.getName()) {
//                case "Client":
//                    expr = "$this->cl_nom . ' ' . $this->cl_prenom";
//                    break;
//                case "Command":
//                    expr = "$this->co_date->format('Y-m-d H:i:s') . ' - ' + $this->client->__toString()";
//                    break;
//                case "Produit":
//                    expr = fields.stream()
//                            .filter(f -> Utils.mapType(f.getType()).equals("string"))
//                            .findFirst()
//                            .map(f -> "$this->" + f.getName())
//                            .orElse("''");
//                    break;
//                default:
//                    expr = fields.isEmpty() ? "''" : "$this->" + fields.get(0).getName();
//            }
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
