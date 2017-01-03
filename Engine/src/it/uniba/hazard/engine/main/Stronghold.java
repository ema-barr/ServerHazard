package it.uniba.hazard.engine.main;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.map.Location;

public class Stronghold {
    private String objectID;
    private Location location;
    private Emergency emergency;

    public Stronghold(Location location, Emergency emergency){
        this.objectID = this.getClass().getName() + "_" + location.toString() + "_" + emergency.toString();
        this.location = location;
        this.emergency = emergency;
    }

    public String getObjectID() {
        return objectID;
    }

    public Location getLocation() {
        return location;
    }

    public Emergency getEmergency() {
        return emergency;
    }

    @Override
    public String toString() {
        return location.toString() + "_" + emergency.toString();
    }
}
