package com.group.univ;

import com.group.univ.generator.*;
import com.group.univ.model.Entity;
import com.group.univ.parser.XMLParser;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.Map;

/**
 * Main class to run the application.
 * This class is responsible for loading the XML document, parsing it, and generating the necessary files.
 */
public class Main {
    public static void main(String[] args) {
        XMLParser xmlParser = new XMLParser();
        PHPGeneratorEntity phpGeneratorEntity = new PHPGeneratorEntity();
        PHPGeneratorController phpGeneratorController = new PHPGeneratorController();
        PHPGeneratorRepository phpGeneratorRepository = new PHPGeneratorRepository();
        PHPGeneratorType phpGeneratorType = new PHPGeneratorType();
        TWIGGeneratorCreate twigGeneratorCreate = new TWIGGeneratorCreate();
        TWIGGeneratorIndex twigGeneratorIndex = new TWIGGeneratorIndex();
        try{
            Document document = xmlParser.loadXmlDocument("src/main/resources/com/group/univ/xml/schema.xml");
            Map<String, Entity> entities = xmlParser.parseEntities(document);
            xmlParser.parseRelations(document, entities);
            phpGeneratorEntity.generatePhpFiles(entities);
            phpGeneratorController.generatePhpControllers(entities);
            phpGeneratorRepository.generatePhpRepositories(entities);
            phpGeneratorType.generateFormFiles(entities);
            twigGeneratorCreate.generateTwigFiles(entities);
            twigGeneratorIndex.generateTwigFiles(entities);
            System.out.println("Génération terminée avec succès !");

        }catch (IOException e){
            System.out.println("Erreur d'entrée/sortie : " + e.getMessage());
        } catch (Exception e){
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}