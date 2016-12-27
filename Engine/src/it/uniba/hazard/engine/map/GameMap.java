package it.uniba.hazard.engine.map;

import it.uniba.hazard.engine.pawns.GamePawn;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

/**
 * Created by andrea_iovine on 24/12/2016.
 */
public class GameMap {
    private Graph<Location, DefaultEdge> mapGraph;
    private List<Area> areas;
    private Map<GamePawn, Location> pieceLocations;

    public GameMap(Graph<Location, DefaultEdge> mapGraph, List<Area> areas) {
        this.mapGraph = mapGraph;
        this.areas = areas;
        pieceLocations = new HashMap<GamePawn, Location>();
    }

    public Set<Location> getAdjacentLocations(GamePawn p) {
        //Get the location of the selected piece
        Location currentPieceLocation = pieceLocations.get(p);
        //Get all the edges of the graph connected to the previous location
        Set<DefaultEdge> edges = mapGraph.edgesOf(currentPieceLocation);
        //List of adjacent locations
        Set<Location> possibleLocations = new HashSet<Location>();

        for (DefaultEdge edge : edges) {
            Location l = mapGraph.getEdgeTarget(edge);
            if (!possibleLocations.contains(l) && l != currentPieceLocation) {
                possibleLocations.add(l);
            }
        }

        return possibleLocations;
    }

    public void placePiece(GamePawn p, Location l) {
        if (pieceLocations.containsKey(p)) {
            pieceLocations.remove(p);
        }
        pieceLocations.put(p, l);
    }

    public Location getLocation(GamePawn p) {
        return pieceLocations.get(p);
    }

    public Set<Location> getAllLocations() {
        return mapGraph.vertexSet();
    }

    public boolean containsPiece(GamePawn p) {
        return pieceLocations.containsKey(p);
    }
}
