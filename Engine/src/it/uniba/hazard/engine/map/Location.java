package it.uniba.hazard.engine.map;

import it.uniba.hazard.engine.exception.InvalidEmergencyLevelException;
import it.uniba.hazard.engine.main.Emergency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andrea_iovine on 24/12/2016.
 */
public class Location implements Comparable<Location>{
    private String objectID;
    private String name;
    private Map<Emergency, Integer> emergencyLevels;
    private boolean isQuarantined;

    public Location(String name, List<Emergency> emergencies) {
        this.objectID = this.getClass().getName() + "_" + name;
        this.name = name;
        emergencyLevels = new HashMap<Emergency, Integer>();
        isQuarantined = false;

        for(Emergency e : emergencies) {
            emergencyLevels.put(e, 0);
        }
    }

    public String getObjectID() {
        return objectID;
    }

    //WARNING: Do not call this outside of the GameState class
    public void setEmergencyLevel(Emergency e, int level) {
        if (level < 0) {
            throw new InvalidEmergencyLevelException("The value is not allowed.");
        }
        if (emergencyLevels.containsKey(e)) {
            emergencyLevels.remove(e);
        }
        emergencyLevels.put(e, level);
    }

    //WARNING: Do not call this outside of the GameState class
    public void setQuarantined(boolean isQuarantined) {
        this.isQuarantined = isQuarantined;
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

    public boolean isQuarantined() {
        return isQuarantined;
    }
}
