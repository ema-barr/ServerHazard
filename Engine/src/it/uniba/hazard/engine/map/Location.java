package it.uniba.hazard.engine.map;

import it.uniba.hazard.engine.main.Emergency;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrea_iovine on 24/12/2016.
 */
public class Location implements Comparable<Location>{
    private String name;
    private Map<Emergency, Integer> emergencyLevels;

    public Location(String name) {
        this.name = name;
        emergencyLevels = new HashMap<Emergency, Integer>();
    }

    public void setEmergencyLevel(Emergency e, int level) {
        if (emergencyLevels.containsKey(e)) {
            emergencyLevels.remove(e);
        }
        emergencyLevels.put(e, level);
    }

    public int getEmergencyLevel(Emergency e) {
        return emergencyLevels.get(e);
    }

    public String toString() {
        return this.name;
    }

    public int compareTo(Location o) {
        return name.compareTo(o.name);
    }
}
