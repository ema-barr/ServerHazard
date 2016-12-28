package it.uniba.hazard.engine.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by andrea_iovine on 24/12/2016.
 */
public class CardManager<C> {

    public List<Card> cards;

    public CardManager(List<Card> cards) {
        this.cards = cards;
    }




    //restituisce una numero di carte random
    public List<Card> getCards(int numberCards){

        List<Card> extractCards = new ArrayList();

        for(int i = 0; i < numberCards; i++) {
            int number = numRandom();
            extractCards.add(cards.get(number));
            cards.remove(number);
        }

        return  extractCards;
    }

    private int numRandom(){
        Random random = new Random();
        int number = random.nextInt(cards.size()-1);
        return number;
    }

}
