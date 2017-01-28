package it.uniba.hazard.engine.turn;

import it.uniba.hazard.engine.cards.Card;
import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.ActionPawn;
import it.uniba.hazard.engine.pawns.GamePawn;

import java.util.List;
import java.util.Set;

/**
 * Created by maccn on 25/12/2016.
 */

/*
    azioniRimanenti: numero di azioni rimanenti, anche dello stesso tipo
 */
public class ActionTurn implements PlayerTurn {

    private ActionGroup player;
    private int pawns;

    private List<Card> bonusCards;

    private int numActions = 4;

    public ActionTurn (ActionGroup pl, int pa) {
        player = pl;
        pawns = pa;
    }





    private void movePawn (GameState gameState, String pawnStr, String currentLocationStr, String newLocationStr) {
        Set<Location> ls = gameState.getMapLocations();
        Location currentLocation = null;

        for (Location l : ls) {
            if (l.toString().equals(currentLocationStr)) {
                currentLocation = l;
            }
        }

        if (currentLocation != null) {
            ActionPawn pawn = null;
            Set<GamePawn> ps = gameState.getPawnsOnLocation(currentLocation);
            for (GamePawn p : ps) {
                ActionPawn temp = (ActionPawn) p;
                if (temp.getObjectID().equals(pawnStr)) {
                    pawn = temp;
                }
            }

            if (pawn != null) {
                ls = gameState.getAdjacentLocations(pawn);
                Location newLocation = null;
                for (Location l : ls) {
                    if (l.toString().equals(newLocationStr)) {
                        newLocation = l;
                    }
                }

                if (newLocation != null) {
                    //gameState.removePawn(pawn);
                    gameState.movePawn(pawn, newLocation);
                }
            }
        }
    }

    private void heal (GameState gameState) {

    }

    private void getResources (GameState gameState) {

    }

    private void placeStronghold (GameState gameState) {
        // TODO:
        // Stronghold s = new Stronghold();
        // gameState.placeStronghold(s);
    }

    // metodo per settare le carte bonus
    private void setBonusCards (GameState gameState) {

    }

    private void useBonusCard (GameState gameState) {

    }

    @Override
    public void startTurn(GameState gameState) {

    }

    @Override
    public void runCommand(GameState gameState, String[] param) {
        switch (param[0]) {
            case "movePawn":
                this.movePawn(gameState, param[1], param[2], param[3]);
                break;
            case "heal":
                this.heal(gameState, param[1], param[2], param[3]);
                break;

            case "getResources":
                this.getResources(gameState, param[1], param[2], param[3]);
                break;
            case "buildStronghold":
                this.placeStronghold(gameState, param[1], param[2], param[3]);
                break;
            case "getBonusCards":
                this.setBonusCards(gameState, param[1], param[2], param[3]);
                break;
            case "useBonusCard":
                this.useBonusCard(gameState, param[1], param[2], param[3]);
                break;
        }
    }
}
