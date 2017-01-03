package it.uniba.hazard.engine.groups;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.Provisions;
import it.uniba.hazard.engine.pawns.ActionPawn;

import java.util.List;

public class ActionGroup {

    public List<Emergency> emergencyToBeSolved;
    public Provisions provisions;
    public ActionPawn actionPawn;
    public String nameActionGroup;

    public ActionGroup(List<Emergency> emergencyToBeSolved, Provisions provisions, ActionPawn actionPawn, String nameActionGroup) {
        this.emergencyToBeSolved = emergencyToBeSolved;
        this.provisions = provisions;
        this.actionPawn = actionPawn;
        this.nameActionGroup = nameActionGroup;
    }

    @Override
    public String toString() {
        return nameActionGroup;
    }
}
