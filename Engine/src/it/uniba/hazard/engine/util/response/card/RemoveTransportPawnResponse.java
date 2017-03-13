package it.uniba.hazard.engine.util.response.card;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.TransportPawn;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by denny on 03/03/2017.
 */
public class RemoveTransportPawnResponse implements Response{

    private boolean success;
    private TransportPawn tp;
    private Location location;
    private String logString;

    public RemoveTransportPawnResponse(boolean success, TransportPawn tp, Location location){
        this.success = success;
        this.tp = tp;
        this.location = location;
        if(success){
            logString = "La pedina " + tp.toString() + " é stata rimossa da " + location.toString();
        }else{
            logString = "Impossibile rimuovere la pedina " + tp.toString() + " da " + location.toString();
        }
    }

    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("TransportPawn", tp.toString());
        res.addProperty("location", location.toString());
        res.addProperty("logString", logString);
        return res.toString();
    }
}
