package planner;

import java.util.*;

public class Dijkstra {
    private Graph graph;

    public Dijkstra(Graph graph) {
        this.graph = graph;
    }

    public List<String> shortestPath(String start, String end) {

        HashMap<String, Double> distance = new HashMap<>();
        HashMap<String, String> previous = new HashMap<>();

        PriorityQueue<Node> queue =
                new PriorityQueue<>(Comparator.comparingDouble(Node::getDistance));

        for (String location : graph.getLocationNames()) {
            distance.put(location, Double.MAX_VALUE);
        }

        distance.put(start, 0.0);
        queue.add(new Node(start, 0));

        while (!queue.isEmpty()) {

            Node current = queue.poll();

            if (current.getName().equals(end))
                break;

            Vertex vertex = graph.getVertex(current.getName());

            for (Edge edge : vertex.getEdges()) {

                String neighbor = edge.getDestination().getName();

                double newDistance =
                        distance.get(current.getName()) + edge.getDistance();

                if (newDistance < distance.get(neighbor)) {

                    distance.put(neighbor, newDistance);
                    previous.put(neighbor, current.getName());

                    queue.add(new Node(neighbor, newDistance));
                }
            }
        }

        ArrayList<String> path = new ArrayList<>();

        String current = end;

        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }

        return path;
    }

    public double getTotalDistance(List<String> path) {

        double total = 0;

        for (int i = 0; i < path.size() - 1; i++) {

            Vertex current = graph.getVertex(path.get(i));

            for (Edge edge : current.getEdges()) {

                if (edge.getDestination().getName().equals(path.get(i + 1))) {
                    total += edge.getDistance();
                    break;
                }
            }
        }

        return total;
    }

    public double getTotalTime(List<String> path) {

        double total = 0;

        for (int i = 0; i < path.size() - 1; i++) {

            Vertex current = graph.getVertex(path.get(i));

            for (Edge edge : current.getEdges()) {

                if (edge.getDestination().getName().equals(path.get(i + 1))) {
                    total += edge.getTime();
                    break;
                }
            }
        }

        return total;
    }

    public double getTotalFare(List<String> path) {

        double total = 0;

        for (int i = 0; i < path.size() - 1; i++) {

            Vertex current = graph.getVertex(path.get(i));

            for (Edge edge : current.getEdges()) {

                if (edge.getDestination().getName().equals(path.get(i + 1))) {
                    total += edge.getFare();
                    break;
                }
            }
        }

        return total;
    }

    private static class Node {

        private String name;
        private double distance;

        public Node(String name, double distance) {
            this.name = name;
            this.distance = distance;
        }

        public String getName() {
            return name;
        }

        public double getDistance() {
            return distance;
        }
    }
}


