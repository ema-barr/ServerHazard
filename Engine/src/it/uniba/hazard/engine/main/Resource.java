package it.uniba.hazard.engine.main;

public class Resource {
    private String objectID;
    private String nameResource;

    public Resource(String nameResource){
        this.objectID = this.getClass().getName();
        this.nameResource = nameResource;
    }

    public String getNameResource() {
        return nameResource;
    }

    @Override
    public String toString() {
        return nameResource;
    }
}
