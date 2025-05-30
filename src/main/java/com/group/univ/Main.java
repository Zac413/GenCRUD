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
        TWIGGeneratorIndexGlobal twigGeneratorIndexGlobal = new TWIGGeneratorIndexGlobal();
        TWIGGeneratorIndex twigGeneratorIndex = new TWIGGeneratorIndex();
        TWIGGeneratorEdit twigGeneratorEdit = new TWIGGeneratorEdit();
        StyleGenerator styleGenerator = new StyleGenerator();
        try{
            Document document = xmlParser.loadXmlDocument("src/main/resources/com/group/univ/xml/schema.xml");
            Map<String, Entity> entities = xmlParser.parseEntities(document);
            xmlParser.parseRelations(document, entities);
            System.out.println(entities.toString());

            phpGeneratorEntity.generatePhpFiles(entities);
            phpGeneratorController.generateIndexController();
            phpGeneratorController.generatePhpControllers(entities);
            twigGeneratorIndexGlobal.generateGlobalIndex(entities);
            phpGeneratorRepository.generatePhpRepositories(entities);
            phpGeneratorType.generateFormFiles(entities);
            twigGeneratorCreate.generateTwigFiles(entities);
            twigGeneratorIndex.generateTwigFiles(entities);
            twigGeneratorEdit.generateTwigFiles(entities);
            styleGenerator.generateBaseTemplate();
            styleGenerator.generateStyleCSS();

            System.out.println("Génération terminée avec succès !");

        }catch (IOException e){
            System.out.println("Erreur d'entrée/sortie : " + e.getMessage());
        } catch (Exception e){
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}