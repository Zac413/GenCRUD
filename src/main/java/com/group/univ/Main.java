package com.group.univ;

import com.group.univ.generator.*;
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
        PHPGeneratorRepository phpGeneratorRepository = new PHPGeneratorRepository();
        PHPGeneratorForms phpGeneratorForms = new PHPGeneratorForms();
        TWIGGeneratorCreate twigGeneratorCreate = new TWIGGeneratorCreate();
        try{
            Document document = xmlParser.loadXmlDocument("src/main/resources/com/group/univ/xml/schema.xml");
            Map<String, Entity> entities = xmlParser.parseEntities(document);
            xmlParser.parseRelations(document, entities);
            phpGeneratorEntity.generatePhpFiles(entities);
            phpGeneratorController.generatePhpControllers(entities);
            phpGeneratorRepository.generatePhpRepositories(entities);
            phpGeneratorForms.generateFormFiles(entities);
            phpGeneratorTemplates.generateTwigTemplates(entities);
            twigGeneratorCreate.generateTwigFiles(entities);
            System.out.println("Génération terminée avec succès !");

        }catch (IOException e){
            System.out.println("Erreur d'entrée/sortie : " + e.getMessage());
        } catch (Exception e){
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}