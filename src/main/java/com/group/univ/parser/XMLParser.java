package com.group.univ.parser;

import com.group.univ.model.Entity;
import com.group.univ.model.Field;
import com.group.univ.model.Relation;

import com.group.univ.utils.Utils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class XMLParser {

    // Charge le document XML
    public Document loadXmlDocument(String filename) throws Exception {
        File xmlFile = new File(filename);
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        return doc;
    }

    // Parse les entités et leurs champs/id
    public Map<String, Entity> parseEntities(Document doc) {
        Map<String, Entity> entities = new LinkedHashMap<>();
        NodeList classNodes = doc.getElementsByTagName("class");
        for (int i = 0; i < classNodes.getLength(); i++) {
            Element classElem = (Element) classNodes.item(i);
            String className = Utils.ucfirst(classElem.getAttribute("name"));
            Entity entity = new Entity(className);

            // Ids
            NodeList idNodes = classElem.getElementsByTagName("id");
            for (int j = 0; j < idNodes.getLength(); j++) {
                Element idElem = (Element) idNodes.item(j);
                String idName = idElem.getAttribute("name");
                entity.getFields().add(new Field(idName, "int", null, null, true));
            }

            // Fields
            NodeList fieldNodes = classElem.getElementsByTagName("field");
            for (int j = 0; j < fieldNodes.getLength(); j++) {
                Element fieldElem = (Element) fieldNodes.item(j);
                String fname = fieldElem.getAttribute("name");
                String ftype = fieldElem.getAttribute("type");
                String fsize = fieldElem.getAttribute("size");
                String fdesc = fieldElem.getAttribute("desc");
                entity.getFields().add(new Field(fname, ftype, fsize, fdesc, false));
            }
            entities.put(className, entity);
        }
        return entities;
    }

    // Parse les relations et les rattache aux entités
    public void parseRelations(Document doc, Map<String, Entity> entities) {
        NodeList relNodes = doc.getElementsByTagName("relation");
        for (int i = 0; i < relNodes.getLength(); i++) {
            Element relElem = (Element) relNodes.item(i);
            String type = relElem.getAttribute("type");
            String from = Utils.ucfirst(relElem.getAttribute("from"));
            String to   = Utils.ucfirst(relElem.getAttribute("to"));
            String name = relElem.getAttribute("name");
            Relation rel = new Relation(type, from, to, name);

            //TODO si oneToMany alors le to ManyToOne

            // Champs de la relation (pour les tables de jointure)
            NodeList relFieldNodes = relElem.getElementsByTagName("field");
            for (int j = 0; j < relFieldNodes.getLength(); j++) {
                Element rfElem = (Element) relFieldNodes.item(j);
                String fname = rfElem.getAttribute("name");
                String ftype = rfElem.getAttribute("type");
                rel.getFields().add(new Field(fname, ftype, null, null, false));
            }

            Entity fromEntity = entities.get(from);
            if (fromEntity != null) fromEntity.getRelations().add(rel);

            Entity toEntity = entities.get(to);
            // gérer les relations dans les deux sens
            if (type.equals("one-to-many") && toEntity != null) {
                String inverseRelName = from.toLowerCase();
                Relation inverseRel = new Relation("many-to-one", to, from, inverseRelName);
                toEntity.getRelations().add(inverseRel);
            }
            if (type.equals("many-to-many") && toEntity != null) {
                String inverseRelName = from.toLowerCase();
                Relation inverseRel = new Relation("many-to-many", to, from, inverseRelName, true);
                toEntity.getRelations().add(inverseRel);
            }
        }
    }
}
