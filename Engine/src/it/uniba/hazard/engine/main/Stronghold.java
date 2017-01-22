package it.uniba.hazard.engine.main;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.map.Location;

public class Stronghold {
    private String objectID;
    private Location location;
    private Emergency emergency;
    private Resource resourceNeeded;
    private int quantityNeeded;

    public Stronghold(Location location, Emergency emergency, Resource resourceNeeded, int quantityNeeded){
        this.objectID = this.getClass().getName() + "_" + location.toString() + "_" + emergency.toString();
        this.location = location;
        this.emergency = emergency;
        this.resourceNeeded = resourceNeeded;
        this.quantityNeeded = quantityNeeded;
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

    public Resource getResourceNeeded() {return resourceNeeded;}

    public int getQuantityNeeded(){return quantityNeeded;}

    public void setQuantityNeeded(int quantity) {quantityNeeded = quantity;}

    @Override
    public String toString() {
        return location.toString() + "_" + emergency.toString();
    }
}
