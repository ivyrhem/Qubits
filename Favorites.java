package planner;

import java.util.ArrayList;

public class Favorites {
    private final String FILE = "favorites.csv";

    public void addFavorite(Route route) {

        FileManager.save(FILE,
                String.join("->", route.getPath()) + "," +
                        route.getDistance() + "," +
                        route.getTime() + "," +
                        route.getFare());

    }

    public ArrayList<String> getFavorites() {
        return FileManager.load(FILE);
    }
}

