package it.uniba.hazard.engine.pawns;

import it.uniba.hazard.engine.groups.ActionGroup;

//Pedina organizzazione
public class ActionPawn implements  PlayerPawn{
    private String objectID;
    private ActionGroup actionGroup;

    public ActionPawn(ActionGroup actionGroup){
        this.objectID = this.getClass().getName() + "_" + actionGroup.toString();
        this.actionGroup = actionGroup;
    }

    public String getObjectID() {
        return objectID;
    }

    public ActionGroup getActionGroup() {
        return actionGroup;
    }
}
