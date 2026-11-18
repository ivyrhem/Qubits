package planner;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private HashMap<String, Vertex> vertices;

    public Graph() {
        vertices = new HashMap<>();
    }

    public void addLocation(String name) {
        vertices.putIfAbsent(name, new Vertex(name));
    }

    public void addRoad(String from, String to,
                        double distance,
                        double time,
                        double fare) {

        Vertex source = vertices.get(from);
        Vertex destination = vertices.get(to);

        if (source != null && destination != null) {
            source.addEdge(destination, distance, time, fare);
            destination.addEdge(source, distance, time, fare);
        }
    }

    public Vertex getVertex(String name) {
        return vertices.get(name);
    }

    public ArrayList<String> getLocationNames() {
        return new ArrayList<>(vertices.keySet());
    }
}

