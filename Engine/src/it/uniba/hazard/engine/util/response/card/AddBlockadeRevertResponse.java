package it.uniba.hazard.engine.util.response.card;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;

import java.util.ArrayList;

/**
 * Created by denny on 03/03/2017.
 */
public class AddBlockadeRevertResponse implements Response{
    private boolean success;
    private ArrayList<Location> locationsUnlocked;
    private String logString;

    public AddBlockadeRevertResponse(boolean success, ArrayList<Location> locationsUnlocked){
        this.locationsUnlocked = locationsUnlocked;
        this.success = success;
        if(success){
            logString = "Barriera rimossa tra ";
            for (Location loc: locationsUnlocked){
                logString += loc + " ";
            }
        }else{
            logString = "Impossibile rimuovere la barriera";
        }
    }


    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        JsonArray array = new JsonArray();
        for (Location loc: locationsUnlocked){
            array.add(loc.toString());
        }
        res.addProperty("success", success);
        res.add("locationsUnlocked", array);
        res.addProperty("logString", logString);
        return res.toString();
    }
}
