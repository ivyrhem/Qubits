package planner;

import java.util.List;

public class Route {
    private List<String> path;
    private double distance;
    private double time;
    private double fare;

    public Route(List<String> path, double distance, double time, double fare) {
        this.path = path;
        this.distance = distance;
        this.time = time;
        this.fare = fare;
    }

    public List<String> getPath() {
        return path;
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

    @Override
    public String toString() {
        return String.join(" -> ", path) +
                "\nDistance: " + distance + " km" +
                "\nTime: " + time + " mins" +
                "\nFare: ₱" + fare;
    }
}

