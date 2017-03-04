package it.uniba.hazard.engine.util.xml_reader;

import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.Repository;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.turn.ActionTurn;
import it.uniba.hazard.engine.turn.EmergencyTurn;
import it.uniba.hazard.engine.turn.EventTurn;
import it.uniba.hazard.engine.turn.ProductionTurn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TurnReader {

    private static ArrayList<Turn> turnOrder = new ArrayList<Turn>();

    public static List<EmergencyTurn> readEmergencyTurns(String path){
        try{
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            Element gameElem = (Element) doc.getElementsByTagName("game").item(0);
            Element turnsElem = (Element) gameElem.getElementsByTagName("turns").item(0);

            NodeList emergencyTurnsList = turnsElem.getElementsByTagName("emergencyTurn");

            ArrayList<EmergencyTurn> emergencyTurns = new ArrayList<EmergencyTurn>();

            for(int i = 0; i < emergencyTurnsList.getLength(); i++){
                Element element = (Element) emergencyTurnsList.item(i);

                System.out.println("name: " + element.getElementsByTagName("name").item(0).getTextContent());
                System.out.println("emergency: " + element.getElementsByTagName("emergency").item(0).getTextContent());
                Emergency emergency = (Emergency) Repository.getFromRepository(Emergency.class.getName() + "_" +
                        element.getElementsByTagName("emergency").item(0).getTextContent());
                EmergencyTurn emergencyTurn = new EmergencyTurn(emergency);
                emergencyTurns.add(emergencyTurn);
                System.out.println("ordNum: " + element.getElementsByTagName("ordNum").item(0).getTextContent());
                int ordNum = Integer.parseInt(element.getElementsByTagName("ordNum").item(0).getTextContent());
                turnOrder.add(ordNum, (Turn) emergencyTurn);
            }
            return emergencyTurns;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<ActionTurn> readActionTurns(String path){
        try{
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            Element gameElem = (Element) doc.getElementsByTagName("game").item(0);
            Element turnsElem = (Element) gameElem.getElementsByTagName("turns").item(0);

            NodeList actionTurnsList = turnsElem.getElementsByTagName("actionTurn");

            ArrayList<ActionTurn> actionTurns = new ArrayList<ActionTurn>();

            for(int i = 0; i < actionTurnsList.getLength(); i++){
                Element element = (Element) actionTurnsList.item(i);

                System.out.println("group: " + element.getElementsByTagName("group").item(0).getTextContent());
                                ActionGroup actionGroup = (ActionGroup) Repository.getFromRepository(ActionGroup.class.getName() + "_" +
                    element.getElementsByTagName("group").item(0).getTextContent());

                System.out.println("numOfActions: " + element.getElementsByTagName("numOfActions").item(0).getTextContent());
                System.out.println("ordNum: " + element.getElementsByTagName("ordNum").item(0).getTextContent());
                ActionTurn actionTurn = new ActionTurn(actionGroup, 1);
                actionTurns.add(actionTurn);
                int ordNum = Integer.parseInt(element.getElementsByTagName("ordNum").item(0).getTextContent());
                turnOrder.add(ordNum, (Turn) actionTurn);
            }
            return actionTurns;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<ProductionTurn> readProductionTurns(String path){
        try{
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            Element gameElem = (Element) doc.getElementsByTagName("game").item(0);
            Element turnsElem = (Element) gameElem.getElementsByTagName("turns").item(0);

            NodeList productionTurnsList = turnsElem.getElementsByTagName("productionTurn");

            ArrayList<ProductionTurn> productionTurns = new ArrayList<ProductionTurn>();

            for(int i = 0; i < productionTurnsList.getLength(); i++){
                Element element = (Element) productionTurnsList.item(i);

                System.out.println("group: " + element.getElementsByTagName("group").item(0).getTextContent());
                ProductionGroup productionGroup = (ProductionGroup) Repository.getFromRepository(ProductionGroup.class.getName() + "_" +
                        element.getElementsByTagName("group").item(0).getTextContent());

                System.out.println("numOfProductionCards: " + element.getElementsByTagName("numOfProductionCards").item(0).getTextContent());
                System.out.println("ordNum: " + element.getElementsByTagName("ordNum").item(0).getTextContent());
                System.out.println("numMovesPerTransportPawn: " + element.getElementsByTagName("numMovesPerTransportPawn").item(0).getTextContent());
                int numMovesPerTransportPawn = Integer.parseInt(element.getElementsByTagName("numMovesPerTransportPawn").item(0).getTextContent());
                ProductionTurn productionTurn = new ProductionTurn(productionGroup, 0, productionGroup.getMaxTransportPawns(), numMovesPerTransportPawn);
                productionTurns.add(productionTurn);
                int ordNum = Integer.parseInt(element.getElementsByTagName("ordNum").item(0).getTextContent());
                turnOrder.add(ordNum, (Turn) productionTurn);
            }
            return productionTurns;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int readNumOfProductionCards(String path){
        try{
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            Element gameElem = (Element) doc.getElementsByTagName("game").item(0);
            Element turnsElem = (Element) gameElem.getElementsByTagName("turns").item(0);

            Element numOfProdElem = (Element) turnsElem.getElementsByTagName("numOfProductionCards").item(0);
            return Integer.parseInt(numOfProdElem.getTextContent());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<EventTurn> readEventTurns(String path){
        try{
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            Element gameElem = (Element) doc.getElementsByTagName("game").item(0);
            Element turnsElem = (Element) gameElem.getElementsByTagName("turns").item(0);

            NodeList eventTurnsList = turnsElem.getElementsByTagName("eventTurn");

            ArrayList<EventTurn> eventTurns = new ArrayList<EventTurn>();

            for(int i = 0; i < eventTurnsList.getLength(); i++){
                Element element = (Element) eventTurnsList.item(i);

                System.out.println("numCardsToDraw: " + element.getElementsByTagName("numCardsToDraw").item(0).getTextContent());
                System.out.println("ordNum: " + element.getElementsByTagName("ordNum").item(0).getTextContent());
                int numCardsToDraw = Integer.parseInt(element.getElementsByTagName("numCardsToDraw").item(0).getTextContent());
                int ordNum = Integer.parseInt(element.getElementsByTagName("ordNum").item(0).getTextContent());
                EventTurn eventTurn = new EventTurn(numCardsToDraw, ordNum);
                eventTurns.add(eventTurn);

                turnOrder.add(ordNum, (Turn) eventTurn);
            }
            return eventTurns;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Turn> readTurnOrder(String path){
        return turnOrder;
    }



}
