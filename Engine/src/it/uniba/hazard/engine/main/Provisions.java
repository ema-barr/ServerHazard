package it.uniba.hazard.engine.main;

import it.uniba.hazard.engine.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Provisions {
    private static int id = -1;
    private String objectID;
    private Map<Resource, Integer> resources;

    public Provisions(){
        id++;
        objectID = this.getClass().getName() + "_" + id;
        this.resources = new HashMap<Resource, Integer>();
    }

    public Provisions(Map<Resource, Integer> resources){
        id++;
        objectID = this.getClass().getName() + "_" + id;
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

    public void insertRes(Resource res, Integer quantity){
        if (resources.containsKey(res)){
            int oldQuant = resources.get(res);
            resources.replace(res, oldQuant + quantity);
        } else {
            resources.put(res, quantity);
        }
    }

    public void removeRes(Resource res){
        if(resources.containsKey(res)){
            resources.remove(res);
        } else {
            throw new ResourceNotFoundException("Resource " + res.getNameResource() + " not in provisions");
        }
    }



    public Map<Resource, Integer> getResources() {
        return resources;
    }

    @Override
    public String toString() {
        return objectID;
    }
}
