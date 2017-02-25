package it.uniba.hazard.engine.groups;

import it.uniba.hazard.engine.exception.CannotMovePawnException;
import it.uniba.hazard.engine.exception.InsufficientResourcesException;
import it.uniba.hazard.engine.exception.EmergencyMismatchException;
import it.uniba.hazard.engine.main.*;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.ActionPawn;
import it.uniba.hazard.engine.pawns.TransportPawn;

import java.util.List;

public class ActionGroup {

    private String objectID;
    private List<Emergency> emergencyToBeSolved;
    private List<Resource> usedRes;
    private Provisions provisions;
    private ActionPawn actionPawn;
    private String nameActionGroup;
    private List<Location> HQs;
    private Location startingPoint;

    public ActionGroup(List<Emergency> emergencyToBeSolved, List<Resource> usedRes, Provisions provisions,
                       ActionPawn actionPawn, String nameActionGroup, List<Location> HQs, Location startingPoint) {
        this.objectID = this.getClass().getName() + "_" + nameActionGroup;
        this.emergencyToBeSolved = emergencyToBeSolved;
        this.usedRes = usedRes;
        this.provisions = provisions;
        this.actionPawn = assignActionPawn();
        this.nameActionGroup = nameActionGroup;
        this.HQs = HQs;
        this.startingPoint = startingPoint;
    }

    private ActionPawn assignActionPawn() {
        ActionPawn actionPawn = new ActionPawn(this);
        return actionPawn;
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

    public void takeResources(GameState state, TransportPawn tp) {
        Provisions prov = state.takeResources(usedRes, actionPawn, tp);
        for (Resource r : prov.getListResources()) {
            provisions.addResource(r, prov.getQuantity(r));
        }
    }

    public List<Location> getHQs() {
        return HQs;
    }

    public Location getStartingPoint() {
        return startingPoint;
    }

    public void solveEmergency(GameState state, Emergency toSolve) {
        //Check if the emergency can be solved by this group
        if (!emergencyToBeSolved.contains(toSolve)) {
            throw new EmergencyMismatchException("This emergency cannot be solved by this group.");
        }
        //Check if there is sufficient resources to solve the emergency
        Resource res = toSolve.getResourceNeeded();
        int resQuantity = provisions.getQuantity(res);
        if (resQuantity <= 0) {
            throw new InsufficientResourcesException("Not enough resources to execute the requested action.");
        } else {
            //If there is, withdraw the resources from the group's deposit
            provisions.withdrawResource(res);
            provisions.addResource(res, resQuantity - 1);
        }
        state.solveEmergency(toSolve, state.getLocationInMap(actionPawn));
    }

    public void buildStronghold(GameState state, Emergency e, Location l) {
        StrongholdInfo si = (StrongholdInfo) state.getRepository().getFromRepository("StrongholdInfo_" + e.getNameEmergency());
        //Check if the emergency can be solved by this group
        if (!emergencyToBeSolved.contains(e)) {
            throw new EmergencyMismatchException("This emergency cannot be solved by this group.");
        }
        //Check if there is sufficient resources to solve the emergency
        Resource res = si.getResourceNeeded();
        int resQuantity = provisions.getQuantity(res);
        if (resQuantity - state.getCurrentStrongholdCost() <= 0) {
            throw new InsufficientResourcesException("Not enough resources to execute the requested action.");
        } else {
            //If there is, withdraw the resources from the group's deposit
            provisions.withdrawResource(res);
            provisions.addResource(res, resQuantity - state.getCurrentStrongholdCost());
            Stronghold s = new Stronghold(l, si);
            state.placeStronghold(s);
        }
    }

    public void moveActionPawn(GameState state, Location l) {
        state.movePawn(actionPawn, l);
    } //TODO spostare di un passo alla volta controllando che lo spostamento sia adiacente


    @Override
    public String toString() {
        return getNameActionGroup();
    }

}
