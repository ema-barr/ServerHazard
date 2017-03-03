package it.uniba.hazard.engine.util.response.card;

import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by denny on 03/03/2017.
 */
public class AddBlockadeResponse implements Response{
    private boolean success;
    private Location l1;
    private Location l2;
    private String logString;

    public AddBlockadeResponse(boolean success, Location l1, Location l2){
        this.l1 = l1;
        this.l2 = l2;
        this.success = success;
        if(success){
            logString = "Barriera costruita tra " + l1.toString() + " e " + l2.toString();
        }else{
            logString = "Impossibile costruire la barriera tra " + l1.toString() + " e " + l2.toString();
        }
    }


    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("location1", l1.toString());
        res.addProperty("location2", l2.toString());
        res.addProperty("logString", logString);
        return res.toString();
    }
}
