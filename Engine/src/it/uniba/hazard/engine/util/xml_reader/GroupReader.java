package it.uniba.hazard.engine.util.xml_reader;

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

public class GroupReader {

    public static void readActionGrooups(String path) {
        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            Element gameElem = (Element) doc.getElementsByTagName("game").item(0);
            Element groupElem = (Element) gameElem.getElementsByTagName("groups").item(0);

            NodeList actionGroupNodes = groupElem.getElementsByTagName("actionGroup");
            if (actionGroupNodes != null && actionGroupNodes.getLength() > 0) {
                for (int i = 0; i < actionGroupNodes.getLength(); i++) {
                    Node nNode = actionGroupNodes.item(i);
                    System.out.println(nNode.getNodeName());

                    Element element = (Element) nNode;

                    System.out.println("name: " + element.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println("emergencyToSolve: " + element.getElementsByTagName("emergencyToSolve").item(0).getTextContent());

                    Element resourcesUsed = (Element) element.getElementsByTagName("resourcesUsed").item(0);
                    NodeList resourceList = resourcesUsed.getElementsByTagName("resource");
                    if (resourceList != null && resourceList.getLength() > 0) {
                        for (int j = 0; j < resourceList.getLength(); j++) {
                            Element nRes = (Element) resourceList.item(j);
                            System.out.println("resource: " + nRes.getTextContent());
                        }
                    }

                    Element hgs = (Element) element.getElementsByTagName("headquarters").item(0);
                    NodeList hgList = hgs.getElementsByTagName("headquarter");
                    if (hgList != null && hgList.getLength() > 0) {
                        for (int j = 0; j < hgList.getLength(); j++) {
                            Element nHG = (Element) hgList.item(j);
                            System.out.println("headquarter: " + nHG.getTextContent());
                        }
                    }

                    System.out.println("starting point: " + element.getElementsByTagName("startingPoint").item(0).getTextContent() + "\n");
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public static void readProductionGrooups(String path) {
        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            Element gameElem = (Element) doc.getElementsByTagName("game").item(0);
            Element groupElem = (Element) gameElem.getElementsByTagName("groups").item(0);

            NodeList productionGroupNodes = groupElem.getElementsByTagName("productionGroup");
            for (int i = 0; i < productionGroupNodes.getLength(); i++) {
                Node nProductionGroup = productionGroupNodes.item(i);
                System.out.println(nProductionGroup.getNodeName());
                Element element = (Element) nProductionGroup;

                System.out.println("name:" + element.getElementsByTagName("name").item(0).getTextContent());
                System.out.println("max transport pawns:" + element.getElementsByTagName("maxNumTransportPawns")
                        .item(0).getTextContent() + "\n");
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
