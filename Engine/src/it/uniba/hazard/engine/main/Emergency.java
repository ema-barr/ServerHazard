package it.uniba.hazard.engine.main;

public class Emergency {
    private String objectID;
    private String nameEmergency;
    private Resource resourceNeeded;
    private GeneralHazardIndicator generalHazardIndicator;

    public Emergency(String nameEmergency, Resource resourceNeeded, GeneralHazardIndicator generalHazardIndicator){
        this.objectID = this.getClass().getName() + "_" + nameEmergency;
        this.resourceNeeded = resourceNeeded;
        this.nameEmergency = nameEmergency;
        this.generalHazardIndicator = generalHazardIndicator;
    }

    public GeneralHazardIndicator getGeneralHazardIndicator(){
        return generalHazardIndicator;
    }

    public String getObjectID() {
        return objectID;
    }

    public String getNameEmergency() {
        return nameEmergency;
    }

    public Resource getResourceNeeded() {
        return resourceNeeded;
    }

    @Override
    public String toString() {
        return nameEmergency;
    }

    public boolean equals(Object o) {
        return ((Emergency) o).nameEmergency.equals(nameEmergency);
    }
}
