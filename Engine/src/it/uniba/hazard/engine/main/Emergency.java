package it.uniba.hazard.engine.main;

public class Emergency {
    private String nameEmergency;

    public Emergency(String nameEmergency){
        this.nameEmergency = nameEmergency;
    }

    public String getNameEmergency() {
        return nameEmergency;
    }

    @Override
    public String toString() {
        return nameEmergency;
    }
}
