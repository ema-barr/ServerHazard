package it.uniba.hazard.engine.main;

//classe per conservare tutte le informazioni relative ai presidi
public class StrongholdInfo {
    private String objectID;
    private Emergency emergency;
    private Resource resourceNeeded;

    public StrongholdInfo(Emergency emergency, Resource resourceNeeded){
        this.objectID = this.getClass().getName() + "_" + emergency;
        this.emergency = emergency;
        this.resourceNeeded = resourceNeeded;
    }

    public Emergency getEmergency() {
        return emergency;
    }

    public Resource getResourceNeeded() {
        return resourceNeeded;
    }

    public String toString(){
        return this.getClass().getName() + "_" + emergency;
    }
}
