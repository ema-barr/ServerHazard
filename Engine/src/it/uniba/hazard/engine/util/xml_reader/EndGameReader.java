package it.uniba.hazard.engine.util.xml_reader;

import it.uniba.hazard.engine.endgame.EndCondition;
import it.uniba.hazard.engine.endgame.LossCondition;
import it.uniba.hazard.engine.endgame.VictoryCondition;
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

public class EndGameReader {
    public static List<EndCondition> readEndConditions(String path){
        try{
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            Element gameElem = (Element) doc.getElementsByTagName("game").item(0);
            Element endGameElem = (Element) gameElem.getElementsByTagName("endGame").item(0);

            NodeList victCondList = endGameElem.getElementsByTagName("victoryCondition");
            ArrayList<EndCondition> endConditionsList = new ArrayList<EndCondition>();

            for (int i = 0; i< victCondList.getLength(); i++){
                Element element = (Element) victCondList.item(i);
                System.out.println("message: " + element.getElementsByTagName("victoryMessage").item(0).getTextContent());
                System.out.println("type: " + element.getElementsByTagName("type").item(0).getTextContent());
                String type = element.getElementsByTagName("type").item(0).getTextContent();

                //TODO da rivedere
                Class victoryClass = Class.forName(type);
                VictoryCondition vc = (VictoryCondition) victoryClass.newInstance();

                endConditionsList.add(vc);
            }

            NodeList lossCondList = endGameElem.getElementsByTagName("lossCondition");
            for (int i = 0; i< lossCondList.getLength(); i++){
                Element element = (Element) lossCondList.item(i);
                System.out.println("message: " + element.getElementsByTagName("lossMessage").item(0).getTextContent());
                System.out.println("type: " + element.getElementsByTagName("type").item(0).getTextContent());
                String type = element.getElementsByTagName("type").item(0).getTextContent();

                //TODO da rivedere
                Class lossClass = Class.forName(type);
                LossCondition lc = (LossCondition) lossClass.newInstance();

                endConditionsList.add(lc);
            }

            return endConditionsList;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

}
