package it.uniba.hazard.engine.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Provisions {
    private Map<Resource, Integer> resources;

    public Provisions(Map<Resource, Integer> resources){
        this.resources = resources;
    }

    public List<Resource> getListResources(){
        ArrayList<Resource> listResources = new ArrayList<Resource>();
        for(Resource resource: resources.keySet()){
            listResources.add(resource);
        }
        return listResources;
    }

    public int getQuantity(Resource res){
        int quantity = -1;
        for(Resource resource: resources.keySet()){
            if(res.equals(resource)){
                quantity =  resources.get(resource);
            }
        }
        return quantity;
    }

    public Map<Resource, Integer> getResources() {
        return resources;
    }
}
