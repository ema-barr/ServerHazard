package it.uniba.hazard.engine.groups;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.Provisions;

import java.util.List;

public class ActionGroup {

    public List<Emergency> emergencyToBeSolved;
    public Provisions provisions;
    public ActionGroup actionGroup;

    public ActionGroup(List<Emergency> emergencyToBeSolved, Provisions provisions, ActionGroup actionGroup) {
        this.emergencyToBeSolved = emergencyToBeSolved;
        this.provisions = provisions;
        this.actionGroup = actionGroup;
    }






}
