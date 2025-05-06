package com.group.univ;

import com.group.univ.generator.PHPGeneratorController;
import com.group.univ.generator.PHPGeneratorEntity;
import com.group.univ.generator.PHPGeneratorTemplates;
import com.group.univ.model.Entity;
import com.group.univ.parser.XMLParser;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        XMLParser xmlParser = new XMLParser();
        PHPGeneratorEntity phpGeneratorEntity = new PHPGeneratorEntity();
        PHPGeneratorTemplates phpGeneratorTemplates = new PHPGeneratorTemplates();
        PHPGeneratorController phpGeneratorController = new PHPGeneratorController();
        try{
            Document document = xmlParser.loadXmlDocument("src/main/resources/com/group/univ/xml/schema.xml");
            Map<String, Entity> entities = xmlParser.parseEntities(document);
            xmlParser.parseRelations(document, entities);
            phpGeneratorEntity.generatePhpFiles(entities);
            phpGeneratorController.generatePhpControllers(entities);
            phpGeneratorTemplates.generateTwigTemplates(entities);
            System.out.println("Génération terminée avec succès !");

        }catch (IOException e){
            System.out.println("Erreur d'entrée/sortie : " + e.getMessage());
        } catch (Exception e){
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}