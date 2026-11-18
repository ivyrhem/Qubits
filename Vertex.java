package planner;

import java.util.ArrayList;

public class Vertex {
    private String name;
    private ArrayList<Edge> edges;

    public Vertex(String name) {
        this.name = name;
        edges = new ArrayList<>();
    }

    public void addEdge(Vertex destination, double distance, double time, double fare) {
        edges.add(new Edge(destination, distance, time, fare));
    }

    public String getName() {
        return name;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return name;
    }
}

