package it.uniba.hazard.engine.map;

import it.uniba.hazard.engine.exception.CannotCreateBlockadeException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrea_iovine on 05/01/2017.
 * A Blockade represents a block that prevents passage from one location to another.
 * Neither players nor emergencies can pass through a blockade.
 */
public class Blockade {
    private ArrayList<Location> locations;

    public Blockade(Location location1, Location location2) {
        if (!location1.equals(location2)) {
            locations.add(location1);
            locations.add(location2);
        } else {
            throw new CannotCreateBlockadeException("The locations must be different");
        }
    }

    public List<Location> getLocations() {
        return locations;
    }

    public boolean contains(Location l1, Location l2) {
        return locations.contains(l1) && locations.contains(l2);
    }
}
