package it.uniba.hazard.engine.util.xml_reader;

import it.uniba.hazard.engine.cards.BonusCard;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

//TODO capire come si instanziano le Bonus Card
public class CardReader {
    public static Map<BonusCard, Integer> readBonusCards(String path){
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
            for (int i = 0; i < bonusCardNodeList.getLength(); i++){
                Element bonusCardNode = (Element) bonusCardNodeList.item(i);

            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
