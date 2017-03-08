package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.exception.NoClassExist;
import it.uniba.hazard.engine.map.Location;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CardManager<C> {

    public List<C> cards = new ArrayList<C>();

    public List<C> instanceCards = new ArrayList<C>();
    public HashMap<ProductionCard,Integer> instanceProductionCards = new HashMap<ProductionCard,Integer>();

    public CardManager(){};

    public CardManager(List<C> cards) {
        this.cards = cards;
    }

    //restituisce una numero di carte random
    //It may return less than numberCards cards if there are no sufficient cards
    public List<C> getCards(int numberCards){
        resetCards();
        List<C> extractCards = new ArrayList<C>();

        for(int i = 0; (cards.size() > 0 && i < numberCards); i++) {
            int number = numRandom();
            extractCards.add(cards.get(number));
            cards.remove(number);
        }

        return  extractCards;
    }

    //It may return less than numberCards cards if there are no sufficient cards
    public List<C> getProductionCards(List<Location> locationTransportPawns, int numberCards){
        resetProductionCards();
        List<C> extractedCards = new ArrayList<C>();
        int i = 0;
        while(cards.size() > 0 && i < numberCards){
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
        int number = random.nextInt(cards.size());
        return number;
    }

    public void instanceCard(ProductionCard pc, int quantity){
        instanceProductionCards.put(pc,quantity);
    }

    //metodo per istanziare le carte dall'xml
    public void instanceCard(String name, int quantity){
        try {
            try {
                Constructor c = Class.forName("it.uniba.hazard.engine.cards." + name).getConstructor(String.class);
                try {
                    for(int i= 0; i < quantity; i++){
                        instanceCards.add((C) c.newInstance(name));
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            throw new NoClassExist("Classe non istanziata");
        }
    }

    private void resetCards() {
        cards = new ArrayList<C>();
        for(C c : instanceCards) {
            cards.add(c);
        }
    }

    private void resetProductionCards() {
        cards = new ArrayList<C>();
        for (ProductionCard c : instanceProductionCards.keySet()) {
            for (int i = 0; i < instanceProductionCards.get(c); i++) {
                cards.add((C) c);
            }
        }
    }


}
