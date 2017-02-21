package it.uniba.hazard.engine.util.xml_reader;

import it.uniba.hazard.engine.cards.BonusCard;
import it.uniba.hazard.engine.cards.EventCard;
import it.uniba.hazard.engine.cards.ProductionCard;
import it.uniba.hazard.engine.main.Repository;
import it.uniba.hazard.engine.main.Resource;
import it.uniba.hazard.engine.map.Location;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class CardReader {
    public static Map<String, Integer> readBonusCards(String path){
        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            Element gameElem = (Element) doc.getElementsByTagName("game").item(0);
            Element cardsElem = (Element) gameElem.getElementsByTagName("cards").item(0);
            Element bonusElem = (Element) cardsElem.getElementsByTagName("bonusCards").item(0);

            NodeList bonusCardNodeList = bonusElem.getElementsByTagName("bonusCard");
            HashMap<String, Integer> bonusMap = new HashMap<String, Integer>();

            for (int i = 0; i < bonusCardNodeList.getLength(); i++){
                Element bonusCardNode = (Element) bonusCardNodeList.item(i);

                String name = bonusCardNode.getElementsByTagName("name").item(0).getTextContent();
                int quantity = Integer.parseInt(bonusCardNode.getElementsByTagName("quantity").item(0).getTextContent());
                bonusMap.put(name, quantity);
            }
            return bonusMap;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Integer> readEventCards(String path){
        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            Element gameElem = (Element) doc.getElementsByTagName("game").item(0);
            Element cardsElem = (Element) gameElem.getElementsByTagName("cards").item(0);
            Element eventElem = (Element) cardsElem.getElementsByTagName("eventCards").item(0);

            NodeList eventCardNodeList = eventElem.getElementsByTagName("eventCard");
            HashMap<String, Integer> eventMap = new HashMap<String, Integer>();
            for (int i=0; i < eventCardNodeList.getLength(); i++){
                Element eventCardNode = (Element) eventCardNodeList.item(i);

                String name = eventCardNode.getElementsByTagName("name").item(0).getTextContent();
                int quantity = Integer.parseInt(eventCardNode.getElementsByTagName("quantity").item(0).getTextContent());
                eventMap.put(name, quantity);
            }
            return eventMap;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<ProductionCard, Integer> readProductionCards(String path){
        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            Element gameElem = (Element) doc.getElementsByTagName("game").item(0);
            Element cardsElem = (Element) gameElem.getElementsByTagName("cards").item(0);
            Element prodElem = (Element) cardsElem.getElementsByTagName("productionCards").item(0);

            NodeList prodCardNodeList = prodElem.getElementsByTagName("productionCard");
            HashMap<ProductionCard, Integer> prodMap = new HashMap<ProductionCard, Integer>();
            for (int i = 0; i < prodCardNodeList.getLength(); i++){
                Element prodCardNode = (Element) prodCardNodeList.item(i);

                int quantity = Integer.parseInt(prodCardNode.getElementsByTagName("quantity").item(0).getTextContent());
                String l = prodCardNode.getElementsByTagName("location").item(0).getTextContent();
                Location location = (Location) Repository.getFromRepository(Location.class.getName() + "_" + l);

                Element resourcesElem = (Element) prodCardNode.getElementsByTagName("resourcesProd").item(0);
                NodeList resourcesProdNodeList = resourcesElem.getElementsByTagName("resourceProd");
                HashMap<Resource, Integer> resourcesProd = new HashMap<Resource, Integer>();
                for (int j = 0; j<resourcesProdNodeList.getLength(); j++){
                    Element resourceProdNode = (Element) resourcesProdNodeList.item(j);

                    String resource = resourceProdNode.getElementsByTagName("resource").item(0).getTextContent();
                    Resource res = (Resource) Repository.getFromRepository(Resource.class.getName() + "_" + resource);
                    int quantRes = Integer.parseInt(resourceProdNode.getElementsByTagName("quantity").item(0).getTextContent());
                    resourcesProd.put(res, quantRes);
                }
                ProductionCard prodCard = new ProductionCard(location, resourcesProd);
                prodMap.put(prodCard, quantity);

            }
            return prodMap;
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
