package it.uniba.hazard.engine.test;

import it.uniba.hazard.engine.connection.ServerConnection;
import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.main.*;
import it.uniba.hazard.engine.map.Area;
import it.uniba.hazard.engine.map.GameMap;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.ActionPawn;
import it.uniba.hazard.engine.turn.ActionTurn;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by isz_d on 28/02/2017.
 */
public class ServerTest {
    public static void main(String[] args) throws URISyntaxException {
        List<Integer> steps = new ArrayList<>();
        steps.add(1);
        steps.add(2);
        GeneralHazardIndicator ghi = new GeneralHazardIndicator(steps);

        Emergency e = new Emergency("malattia", new Resource("risorsa"), ghi);
        List<Emergency> emergencies = new ArrayList<>();
        emergencies.add(e);


        Graph<Location, DefaultEdge> mapGraph = new SimpleGraph<Location, DefaultEdge>(DefaultEdge.class);
        Location l1 = new Location("bari", emergencies);
        Location l2 = new Location("barletta", emergencies);
        Location l3 = new Location("bitonto", emergencies);
        Location l4 = new Location("trani", emergencies);

        List<Location> locations = new ArrayList<>();
        locations.add(l1);
        locations.add(l2);
        locations.add(l3);
        locations.add(l4);

        Area a = new Area(locations);
        List<Area> areas = new ArrayList<>();
        areas.add(a);


        mapGraph.addVertex(l1);
        mapGraph.addVertex(l2);
        mapGraph.addVertex(l3);
        mapGraph.addVertex(l4);

        mapGraph.addEdge(l1, l2);
        mapGraph.addEdge(l2, l3);
        mapGraph.addEdge(l3, l4);
        mapGraph.addEdge(l4, l1);

        GameMap map = new GameMap(mapGraph, areas);

        GameState gs = new GameState(map,
                null,
                null,
                null,
                emergencies,
                null,
                null,
                null,
                5,
                5,
                1,
                null
        );

        ActionGroup ag = new ActionGroup(emergencies, null, new Provisions(), null, "test", null, null);
        List<Turn> turns = new ArrayList<>();
        turns.add(new ActionTurn(ag, 5));
        TurnSequence ts = new TurnSequence(turns);
        ActionPawn p = new ActionPawn(ag);
        map.placePawn(p, l1);
        Game g = new Game(gs, ts, new GameController());
        ServerConnection sc = new ServerConnection("http://localhost:6882", g);
        sc.startConnection();
    }
}