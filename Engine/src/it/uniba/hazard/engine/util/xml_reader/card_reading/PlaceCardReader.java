package it.uniba.hazard.engine.util.xml_reader.card_reading;

import it.uniba.hazard.engine.cards.Card;
import it.uniba.hazard.engine.cards.MovePlace;
import it.uniba.hazard.engine.main.Repository;
import it.uniba.hazard.engine.map.Location;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isz_d on 28/03/2017.
 */
public class PlaceCardReader implements ICardReader {

    @Override
    public List<Card> readCard(Element e) {
        List<Card> result = new ArrayList<>();
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        int quantity = Integer.parseInt(e.getElementsByTagName("quantity").item(0).getTextContent());
        String locationName = e.getElementsByTagName("location").item(0).getTextContent();

        String locationID = Location.class.getName() + "_" + locationName;
        Location l = (Location) Repository.getFromRepository(locationID);

        for (int i = 0; i < quantity; i++) {
            result.add(new MovePlace("MovePlace", l));
        }
        return result;
    }
}
