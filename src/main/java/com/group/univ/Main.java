package com.group.univ;

import com.group.univ.generator.PHPGenerator;
import com.group.univ.model.Entity;
import com.group.univ.parser.XMLParser;
import org.w3c.dom.Document;

import java.util.Map;


public class Main {
    public static void main(String[] args) {
        XMLParser xmlParser = new XMLParser();
        PHPGenerator phpGenerator = new PHPGenerator();
        try{
            Document document = xmlParser.loadXmlDocument("src/main/resources/com/group/univ/xml/schema.xml");
            Map<String, Entity> entities = xmlParser.parseEntities(document);
            xmlParser.parseRelations(document, entities);
            phpGenerator.generatePhpFiles(entities);
        }catch (Exception e){
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}