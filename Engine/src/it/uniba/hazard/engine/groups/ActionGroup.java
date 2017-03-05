package it.uniba.hazard.engine.groups;

import com.google.gson.*;
import it.uniba.hazard.engine.main.*;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.ActionPawn;
import it.uniba.hazard.engine.pawns.TransportPawn;
import it.uniba.hazard.engine.util.response.action_group.ActionGroupMoveResponse;
import it.uniba.hazard.engine.util.response.action_group.BuildStrongholdResponse;
import it.uniba.hazard.engine.util.response.action_group.SolveEmergencyResponse;
import it.uniba.hazard.engine.util.response.action_group.TakeResourceResponse;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

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
                       String nameActionGroup, List<Location> HQs, Location startingPoint) {
        this.objectID = this.getClass().getName() + "_" + nameActionGroup;
        this.emergencyToBeSolved = emergencyToBeSolved;
        this.usedRes = usedRes;
        if (provisions == null){
            this.provisions = new Provisions();
        } else {
            this.provisions = provisions;
        }
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

    public TakeResourceResponse takeResources(GameState state, TransportPawn tp) {
        TakeResourceResponse takeResourceResponse;

        Provisions prov = state.takeResources(usedRes, actionPawn, tp);
        //TODO è il gamestate che dice se le risorse sono state prese o no?
        for (Resource r : prov.getListResources()) {
            provisions.addResource(r, prov.getQuantity(r));
        }
        takeResourceResponse = new TakeResourceResponse(true, tp, this);
        return takeResourceResponse;
    }

    public List<Location> getHQs() {
        return HQs;
    }

    public Location getStartingPoint() {
        return startingPoint;
    }

    public SolveEmergencyResponse solveEmergency(GameState state, Emergency toSolve) {
        SolveEmergencyResponse solveEmergencyResponse;

        //Check if the emergency can be solved by this group
        if (!emergencyToBeSolved.contains(toSolve)) {
            //throw new EmergencyMismatchException("This emergency cannot be solved by this group.");
            solveEmergencyResponse = new SolveEmergencyResponse(false, toSolve, this);
        }
        //Check if there is sufficient resources to solve the emergency
        Resource res = toSolve.getResourceNeeded();
        int resQuantity = provisions.getQuantity(res);
        if (resQuantity <= 0) {
            //throw new InsufficientResourcesException("Not enough resources to execute the requested action.");
            solveEmergencyResponse = new SolveEmergencyResponse(false, toSolve, this);
        } else {
            //If there is, withdraw the resources from the group's deposit
            provisions.withdrawResource(res);
            provisions.addResource(res, resQuantity - 1);
            solveEmergencyResponse = new SolveEmergencyResponse(true, toSolve, this);
        }
        state.solveEmergency(toSolve, state.getLocationInMap(actionPawn));
        return solveEmergencyResponse;
    }

    public BuildStrongholdResponse buildStronghold(GameState state, Emergency e, Location l) {
        BuildStrongholdResponse buildStrongholdResponse;

        StrongholdInfo si = (StrongholdInfo) state.getRepository().getFromRepository("StrongholdInfo_" + e.getNameEmergency());
        //Check if the emergency can be solved by this group
        if (!emergencyToBeSolved.contains(e)) {
            //throw new EmergencyMismatchException("This emergency cannot be solved by this group.");
            buildStrongholdResponse = new BuildStrongholdResponse(false, this, e , l);
        }
        //Check if there is sufficient resources to solve the emergency
        Resource res = si.getResourceNeeded();
        int resQuantity = provisions.getQuantity(res);
        if (resQuantity - state.getCurrentStrongholdCost() <= 0) {
            //throw new InsufficientResourcesException("Not enough resources to execute the requested action.");
            buildStrongholdResponse = new BuildStrongholdResponse(false, this, e , l);
        } else {
            //If there is, withdraw the resources from the group's deposit
            provisions.withdrawResource(res);
            provisions.addResource(res, resQuantity - state.getCurrentStrongholdCost());
            Stronghold s = new Stronghold(l, si);
            state.placeStronghold(s);
            buildStrongholdResponse = new BuildStrongholdResponse(true, this, e , l);
        }
        return buildStrongholdResponse;
    }

    public ActionGroupMoveResponse moveActionPawn(GameState state, Location l) {
        ActionGroupMoveResponse actionGroupMoveResponse;

        Set<Location> adjacentLocations = state.getAdjacentLocations(actionPawn);
        boolean found = false;
        for (Location adjLoc: adjacentLocations){
            if (adjLoc.toString().equals(l.toString())){
                found = true;
                break;
            }
        }
        if (found){
            state.movePawn(actionPawn, l);
            actionGroupMoveResponse = new ActionGroupMoveResponse(true, l, this);
        } else {
            actionGroupMoveResponse = new ActionGroupMoveResponse(false, l , this);
            //throw new CannotMovePawnException("Invalid movement");
        }
        return actionGroupMoveResponse;
    }


    public JsonElement toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ActionGroup.class, new ActionGroupSerializer());
        return gsonBuilder.create().toJsonTree(this);
    }


    @Override
    public String toString() {
        return getNameActionGroup();
    }

    public class ActionGroupSerializer implements JsonSerializer<ActionGroup> {

        @Override
        public JsonElement serialize(ActionGroup actionGroup, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject result = new JsonObject();
            result.addProperty("name", nameActionGroup);
            result.add("resources", provisions.toJson());
            return result;
        }
    }

}
