package it.uniba.hazard.engine.groups;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.Provisions;
import it.uniba.hazard.engine.main.Resource;
import it.uniba.hazard.engine.pawns.ActionPawn;

import java.util.List;

public class ActionGroup {

    private String objectID;
    private List<Emergency> emergencyToBeSolved;
    private List<Resource> usedRes;
    private Provisions provisions;
    private ActionPawn actionPawn;
    private String nameActionGroup;

    public ActionGroup(List<Emergency> emergencyToBeSolved, List<Resource> usedRes, Provisions provisions, ActionPawn actionPawn, String nameActionGroup) {
        this.objectID = this.getClass().getName() + "_" + nameActionGroup;
        this.emergencyToBeSolved = emergencyToBeSolved;
        this.usedRes = usedRes;
        this.provisions = provisions;
        this.actionPawn = actionPawn;
        this.nameActionGroup = nameActionGroup;
    }

    public String getObjectID() {
        return objectID;
    }

    public List<Emergency> getEmergencyToBeSolved() {
        return emergencyToBeSolved;
    }

    public List<Resource> getUsedRes() {
        return usedRes;
    }

    public Provisions getProvisions() {
        return provisions;
    }

    public void setProvisions(Provisions provisions) {
        this.provisions = provisions;
    }

    public ActionPawn getActionPawn() {
        return actionPawn;
    }

    public String getNameActionGroup() {
        return nameActionGroup;
    }

    @Override
    public String toString() {
        return getNameActionGroup();
    }

}
