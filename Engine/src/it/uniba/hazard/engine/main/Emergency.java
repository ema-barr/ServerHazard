package it.uniba.hazard.engine.main;

public class Emergency {
    private String nameEmergency;
    private Resource resourceNeeded;

    public Emergency(String nameEmergency, Resource resourceNeeded){
        this.resourceNeeded = resourceNeeded;
        this.nameEmergency = nameEmergency;
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
