package it.uniba.hazard.engine.util.response.action_turn;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.uniba.hazard.engine.cards.BonusCard;
import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.util.response.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maccn on 03/03/2017.
 */
public class GetBonusCardsResponse implements Response {

    private boolean success;
    private List<BonusCard> bonusCards;
    private ActionGroup actionGroup;
    private String logString;


    public GetBonusCardsResponse (boolean success, ActionGroup group, List<BonusCard> bonusCards) {
        this.success = success;
        this.bonusCards = bonusCards;
        this.actionGroup = group;

        if (success)
            logString = "Il gruppo " + actionGroup.getNameActionGroup() + " ha " + bonusCards.size() + " carte bonus.";
        else
            logString = "Impossibile ricevere le carte bonus dal gruppo " + actionGroup.getNameActionGroup() + ".";
    }

    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        JsonArray bonusCardsArray = new JsonArray();
        for (BonusCard bonusCard: bonusCards){
            bonusCardsArray.add(bonusCard.getObjectID());
        }
        res.addProperty("success", success);
        res.addProperty("actionGroup", actionGroup.getNameActionGroup());
        res.add("bonusCards", bonusCardsArray);
        res.addProperty("logString", logString);
        return res.toString();
    }

}
