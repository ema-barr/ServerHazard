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
    private Map<GamePawn, Location> pawnLocations;

    public GameMap(Graph<Location, DefaultEdge> mapGraph, List<Area> areas) {
        this.mapGraph = mapGraph;
        this.areas = areas;
        pawnLocations = new HashMap<GamePawn, Location>();
    }

    public Set<Location> getAdjacentLocations(GamePawn p) {
        //Get the location of the selected pawn
        Location currentPawnLocation = pawnLocations.get(p);
        //Get all the edges of the graph connected to the previous location
        Set<DefaultEdge> edges = mapGraph.edgesOf(currentPawnLocation);
        //List of adjacent locations
        Set<Location> possibleLocations = new HashSet<Location>();

        for (DefaultEdge edge : edges) {
            Location l = mapGraph.getEdgeTarget(edge);
            if (!possibleLocations.contains(l) && l != currentPawnLocation) {
                possibleLocations.add(l);
            }
        }

        return possibleLocations;
    }

    public void placePawn(GamePawn p, Location l) {
        if (pawnLocations.containsKey(p)) {
            pawnLocations.remove(p);
        }
        pawnLocations.put(p, l);
    }

    public Location getLocation(GamePawn p) {
        return pawnLocations.get(p);
    }

    public Set<Location> getAllLocations() {
        return mapGraph.vertexSet();
    }

    public boolean containsPawn(GamePawn p) {
        return pawnLocations.containsKey(p);
    }
}
