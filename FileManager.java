package planner;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    public static void save(String fileName, String data) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }

    }

    public static ArrayList<String> load(String fileName) {

        ArrayList<String> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {
                list.add(line);
            }

        } catch (IOException e) {
            System.out.println("File not found.");
        }

        return list;
    }
}
