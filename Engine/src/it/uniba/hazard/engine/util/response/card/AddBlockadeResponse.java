package it.uniba.hazard.engine.util.response.card;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.uniba.hazard.engine.main.Repository;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by denny on 03/03/2017.
 */
public class AddBlockadeResponse implements Response{
    private boolean success;
    private String cardName;
    private ArrayList<Location> locationsBlockade;
    private String logString;

    public AddBlockadeResponse(boolean success, String cardName, ArrayList<Location> locationsBlockade){
        this.locationsBlockade = locationsBlockade;
        this.cardName = cardName;
        this.success = success;

        MessageFormat formatter= (MessageFormat) Repository.getFromRepository("messageFormat");
        ResourceBundle messages = (ResourceBundle) Repository.getFromRepository("resourceBundle");

        if(success){
            String locsStr = "";
            for (Location loc : locationsBlockade){
                locsStr += loc.toString() + " ";
            }

            Object[] messageArgs = {locsStr};
            formatter.applyPattern(messages.getString("AddBlockadeResponse_success"));
            logString = formatter.format(messageArgs);
        }else{
            logString = messages.getString("AddBlockadeResponse_failure");
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
        res.addProperty("cardName", cardName);
        res.add("locationsBlockade", array);
        res.addProperty("logString", logString);
        return res.toString();
    }
}
