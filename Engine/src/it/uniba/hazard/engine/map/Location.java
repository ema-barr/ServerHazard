package it.uniba.hazard.engine.map;

/**
 * Created by andrea_iovine on 24/12/2016.
 */
public class Location implements Comparable<Location>{
    private String name;

    public Location(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public int compareTo(Location o) {
        return name.compareTo(o.name);
    }
}
