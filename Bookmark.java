package planner;

import java.util.ArrayList;

public class Bookmark {

    private final String FILE = "bookmarks.csv";

    public void addBookmark(String location) {
        FileManager.save(FILE, location);
    }

    public ArrayList<String> getBookmarks() {
        return FileManager.load(FILE);
    }
}
