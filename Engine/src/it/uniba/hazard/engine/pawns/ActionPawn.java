package it.uniba.hazard.engine.pawns;

import it.uniba.hazard.engine.groups.ActionGroup;

public class ActionPawn implements  PlayerPawn{
    private ActionGroup actionGroup;

    public ActionPawn(ActionGroup actionGroup){
        this.actionGroup = actionGroup;
    }

    public ActionPawn() {

    }
}
