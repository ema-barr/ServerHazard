package it.uniba.hazard.engine.util.xml_reader;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.GeneralHazardIndicator;
import it.uniba.hazard.engine.main.Repository;
import it.uniba.hazard.engine.main.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmergencyReader {
    public static List<Emergency> readEmergencies(String path){
        try{
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            ArrayList<Emergency> emergencies = new ArrayList<Emergency>();

            Element gameElem = (Element) doc.getElementsByTagName("game").item(0);
            Element emergenciesElem = (Element) gameElem.getElementsByTagName("emergencies").item(0);

            NodeList emergenciesList = emergenciesElem.getElementsByTagName("emergency");
            for (int i = 0; i < emergenciesList.getLength(); i++) {
                Element element = (Element) emergenciesList.item(i);
                System.out.println("name:" + element.getElementsByTagName("name").item(0).getTextContent());
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                System.out.println("resource needed:" + element.getElementsByTagName("resourceNeeded").item(0).getTextContent());
                Resource resourceNeeded = (Resource) Repository.getFromRepository(Resource.class.getName() + "_" + name);
                //TODO da rivedere quando si definiranno i metodi per i led
                ArrayList<Integer> steps = new ArrayList<Integer>();

                Element ghiElem = (Element) element.getElementsByTagName("generalHazardIndicator").item(0);
                Element stepsElem = (Element) ghiElem.getElementsByTagName("steps").item(0);

                NodeList stepList = stepsElem.getElementsByTagName("step");
                for (int j = 0; j < stepList.getLength(); j++){
                    Element stepNode = (Element) stepList.item(j);
                    System.out.println("level:" + stepNode.getElementsByTagName("level").item(0).getTextContent());
                    int level = Integer.parseInt(stepNode.getElementsByTagName("level").item(0).getTextContent());
                    System.out.println("level:" + stepNode.getElementsByTagName("led").item(0).getTextContent());

                    steps.add(level);
                }

                GeneralHazardIndicator ghi = new GeneralHazardIndicator(steps);

                Emergency em = new Emergency(name, resourceNeeded, ghi);
                emergencies.add(em);
            }
            return emergencies;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int readMaxGravityLevel(String path){
        try{
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            Element gameElem = (Element) doc.getElementsByTagName("game").item(0);
            Element emergenciesElem = (Element) gameElem.getElementsByTagName("emergencies").item(0);
            Element maxGravityLevel = (Element) emergenciesElem.getElementsByTagName("maxGravityLevel").item(0);

            return Integer.parseInt(maxGravityLevel.getTextContent());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int readStrongholdCost(String path){
        try{
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            Element gameElem = (Element) doc.getElementsByTagName("game").item(0);
            Element emergenciesElem = (Element) gameElem.getElementsByTagName("emergencies").item(0);
            Element strongholdCostElem = (Element) emergenciesElem.getElementsByTagName("strongholdCost").item(0);

            return Integer.parseInt(strongholdCostElem.getTextContent());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<GravityLevel> readGravityLevels(String path){
        try{
            //TODO controllare quando si definiranno i metodi per i led
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            ArrayList<GravityLevel> gravityLevelsList = new ArrayList<GravityLevel>();

            Element gameElem = (Element) doc.getElementsByTagName("game").item(0);
            Element emergenciesElem = (Element) gameElem.getElementsByTagName("emergencies").item(0);
            Element gravityLevels = (Element) emergenciesElem.getElementsByTagName("gravityLevels").item(0);

            NodeList levels = gravityLevels.getElementsByTagName("level");
            for (int i = 0; i < levels.getLength(); i++){
                Element element = (Element) levels.item(i);
                System.out.println("number:" + element.getElementsByTagName("number").item(0).getTextContent());
                int num = Integer.parseInt(element.getElementsByTagName("number").item(0).getTextContent());
                System.out.println("color:" + element.getElementsByTagName("color").item(0).getTextContent());
                String color = element.getElementsByTagName("color").item(0).getTextContent();
                GravityLevel gl = new GravityLevel(num, color);
                gravityLevelsList.add(gl);
            }

            return gravityLevelsList;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }
}
