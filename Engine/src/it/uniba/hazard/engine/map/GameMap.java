package it.uniba.hazard.engine.map;

import it.uniba.hazard.engine.exception.NoSuchPawnException;
import it.uniba.hazard.engine.main.Emergency;
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

    public List<Area> getAreas() {
        return areas;
    }

    public Set<Location> getAdjacentLocations(GamePawn p) {
        //Get the location of the selected pawn
        Location currentPawnLocation = pawnLocations.get(p);
        return getAdjacentLocations(currentPawnLocation);
    }

    public Set<Location> getAdjacentLocations(Location loc) {
        //Get all the edges of the graph connected to the previous location
        Set<DefaultEdge> edges = mapGraph.edgesOf(loc);
        //List of adjacent locations
        Set<Location> possibleLocations = new HashSet<Location>();

        for (DefaultEdge edge : edges) {
            Location l = mapGraph.getEdgeTarget(edge);
            if (!possibleLocations.contains(l) && l != l) {
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

    public void removePawn(GamePawn p) {
        if (pawnLocations.containsKey(p)) {
            pawnLocations.remove(p);
        }
    }

    public Location getLocation(GamePawn p) {
        try {
            return pawnLocations.get(p);
        } catch (NullPointerException e) {
            throw new NoSuchPawnException("The requested pawn is not present");
        }
    }

    public Set<Location> getAllLocations() {
        return mapGraph.vertexSet();
    }

    public boolean containsPawn(GamePawn p) {
        return pawnLocations.containsKey(p);
    }

    public Set<GamePawn> getPawnsOnLocation(Location l) {
        Set<GamePawn> result = new HashSet<GamePawn>();
        for (Map.Entry<GamePawn, Location> entry : pawnLocations.entrySet()) {
            if (entry.getValue().equals(l)) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public void removePath(Location l1, Location l2) {
        mapGraph.removeAllEdges(l1, l2);
    }

    public void addPath(Location l1, Location l2) {
        mapGraph.addEdge(l1, l2);
    }

    public Map<GamePawn, Location> getAllPawns() {
        //Create a copy of the object
        Map<GamePawn, Location> result = new HashMap<GamePawn, Location>();
        for (Map.Entry<GamePawn, Location> e : pawnLocations.entrySet()) {
            result.put(e.getKey(), e.getValue());
        }
        return result;
    }
}
