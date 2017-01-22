package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.map.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardManager<C> {

    public List<C> cards;

    public CardManager(List<C> cards) {
        this.cards = cards;
    }

    //restituisce una numero di carte random
    public List<C> getCards(int numberCards){

        List<C> extractCards = new ArrayList<C>();

        for(int i = 0; i < numberCards; i++) {
            int number = numRandom();
            extractCards.add(cards.get(number));
            cards.remove(number);
        }

        return  extractCards;
    }

    public List<C> getProductionCards(List<Location> locationTransportPawns, int numberCards){
        List<C> extractedCards = new ArrayList<C>();
        int i = 0;
        while(i < numberCards){
            int number = numRandom();
            ProductionCard card = (ProductionCard) cards.get(number);
            Location l = card.getLocation();
            if (!locationTransportPawns.contains(l)){
                extractedCards.add(cards.get(number));
                i++;
            }
            cards.remove(number);

        }
        return extractedCards;
    }

    private int numRandom(){
        Random random = new Random();
        int number = random.nextInt(cards.size()-1);
        return number;
    }

}
