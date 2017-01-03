package it.uniba.hazard.engine.main;

public class Resource {
    private String objectID;
    private String nameResource;
    private Emergency emergencyType;

    public Resource(String nameResource, Emergency emergencyType){
        this.objectID = this.getClass().getName() + "_" + emergencyType.toString();
        this.nameResource = nameResource;
        this.emergencyType = emergencyType;
    }

    public Emergency getEmergencyType() {
        return emergencyType;
    }

    public String getNameResource() {
        return nameResource;
    }

    @Override
    public String toString() {
        return nameResource;
    }
}
