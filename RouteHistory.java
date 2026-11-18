package planner;

import java.util.ArrayList;

public class RouteHistory {
    private final String FILE = "history.csv";

    public void saveHistory(Route route) {

        FileManager.save(FILE,
                String.join("->", route.getPath()) + "," +
                        route.getDistance() + "," +
                        route.getTime() + "," +
                        route.getFare());

    }

    public ArrayList<String> getHistory() {
        return FileManager.load(FILE);
    }
}

