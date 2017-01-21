package it.uniba.hazard.engine.main;


import it.uniba.hazard.engine.exception.InsufficientNumOfLocationsError;
import it.uniba.hazard.engine.map.Location;

import java.util.*;

public class GameInitialization {
    private GameState gs;

    public GameInitialization(){
        gs = new GameState(); //TODO
    }

    /**
     * Initialization of the game
     * @param locationList list of possible locations
     * @param emergency emergency to be initialized
     * @param settings the KEY is the number of locations where the emergency starts with level VALUE
     */
    public void initialize(List<Location> locationList, Emergency emergency, Map<Integer, Integer> settings){
        int numLocations;
        Random random = new Random();
        int randomIndexLoc;

        Set<Integer> keyset = settings.keySet();
        int value;


        //the number of locations where the emergency start must be lower than the number of possible locations
        boolean check = checkNumLocations(locationList, settings);

        if (check){
            for (int key: keyset){
                value = settings.get(key);
                HashMap<Location, Integer> locationsStart = new HashMap<Location,Integer>();


                for (int i = 0; i < key; i++){
                    numLocations = locationList.size();
                    randomIndexLoc = random.nextInt(numLocations);

                    Location l = locationList.get(randomIndexLoc);
                    locationsStart.put(l, value);

                    locationList.remove(randomIndexLoc);
                }
                gs.diffuseEmergency(emergency, locationsStart);
            }

            System.out.println("Setup iniziale di " + emergency.getNameEmergency() +" completato");
        } else {
            throw new InsufficientNumOfLocationsError("The number of location where emergency " +
                    emergency.getNameEmergency() + " starts is greater than the number of possible locations");
        }
    }

    private boolean checkNumLocations(List<Location> locationList, Map<Integer, Integer> settings){
        int numLocations = locationList.size();
        int numLocationsSett = 0;

        Set<Integer> keys = settings.keySet();

        for (int key: keys){
            numLocationsSett = numLocationsSett + key;
        }

        if (numLocations < numLocationsSett){
            return false;
        } else {
            return true;
        }
    }


}
