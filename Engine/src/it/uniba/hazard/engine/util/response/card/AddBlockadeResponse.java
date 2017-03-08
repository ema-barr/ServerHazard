package it.uniba.hazard.engine.util.response.card;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;

import java.util.ArrayList;

/**
 * Created by denny on 03/03/2017.
 */
public class AddBlockadeResponse implements Response{
    private boolean success;
    private ArrayList<Location> locationsBlockade;
    private String logString;

    public AddBlockadeResponse(boolean success, ArrayList<Location> locationsBlockade){
        this.locationsBlockade = locationsBlockade;
        this.success = success;
        if(success){
            logString = "Barriera costruita tra ";
            for (Location loc : locationsBlockade){
                logString += loc.toString() + " ";
            }
        }else{
            logString = "Impossibile costruire la barriera";
        }
    }


    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        JsonArray array = new JsonArray();
        for (Location loc: locationsBlockade){
            array.add(loc.toString());
        }
        res.addProperty("success", success);
        res.addProperty("eventType", "AddBlockade");
        res.add("locationsBlockade", array);
        res.addProperty("logString", logString);
        return res.toString();
    }
}
