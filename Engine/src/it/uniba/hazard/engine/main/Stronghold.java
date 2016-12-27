package it.uniba.hazard.engine.main;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.map.Location;

public class Stronghold {
    private Location location;
    private Emergency emergency;

    public Stronghold(Location location, Emergency emergency){
        this.location = location;
        this.emergency = emergency;
    }

    public Location getLocation() {
        return location;
    }

    public Emergency getEmergency() {
        return emergency;
    }
}
