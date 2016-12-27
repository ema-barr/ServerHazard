package it.uniba.hazard.engine.main;

public class Resource {
    private Emergency emergencyType;

    public Resource(Emergency emergencyType){
        this.emergencyType = emergencyType;
    }

    public Emergency getEmergencyType() {
        return emergencyType;
    }
}
