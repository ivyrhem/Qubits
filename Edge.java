package planner;

public class Edge {
    private Vertex destination;
    private double distance;
    private double time;
    private double fare;

    public Edge(Vertex destination, double distance, double time, double fare) {
        this.destination = destination;
        this.distance = distance;
        this.time = time;
        this.fare = fare;
    }

    public Vertex getDestination() {
        return destination;
    }

    public double getDistance() {
        return distance;
    }

    public double getTime() {
        return time;
    }

    public double getFare() {
        return fare;
    }
}

